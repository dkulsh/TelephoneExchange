package com.impact.backend.repository;

import com.impact.backend.entity.CallData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallDataRepository extends JpaRepository<CallData, Long> {

    long countByTenant(String tenant);

    @Query(value = "select sum(duration) from call_data where tenant = :tenant and action = :action", nativeQuery = true)
    Integer getTotalDurationByTenantAndAction(String tenant, String action);

//    A failure can be in 2 situation:
//          1. When call originated and did NOT ring
//          2. Call originated and DID ring.

    //    If the call was ANSWRED - then its NOT a failure anymore
//    Since the RING status is optional - it has been put into an OUTER JOIN
    @Query(value = "select sum(a.origin_duration) + sum(a.ring_duration) from (" +
            "SELECT " +
            "CASE WHEN origin.duration is not null THEN origin.duration " +
            "     ELSE 0 " +
            "end origin_duration, " +
            "CASE WHEN ring.duration is not null THEN ring.duration " +
            "     ELSE 0 " +
            "end ring_duration " +
            "from call_data origin " +
            "left outer join call_data ring " +
            "on origin.uuid = ring.uuid " +
            "and ring.action = 'RING' " +
            "where origin.action = 'ORIGINATE' " +
            "and origin.tenant = :tenant) a", nativeQuery = true)
    Integer getTotalFailureDurationByTenant(String tenant);

//    A success can be in these situations:
//          1. When call originated, rang, was answered and did NOT HANGUP
//          1. When call originated, rang, was answered and DID HANGUP

//    Since the HANGUP status is optional - it has been put into an OUTER JOIN
    @Query(value = "select sum(a.origin_duration) + sum(a.ring_duration) + sum(a.answer_duration) + sum(a.hangup_duration)" +
            "from (" +
            "SELECT " +
            "CASE WHEN origin.duration is not null THEN origin.duration " +
            "     ELSE 0 " +
            "end origin_duration, " +
            "CASE WHEN ring.duration is not null THEN ring.duration  " +
            "     ELSE 0 " +
            "end ring_duration, " +
            "CASE WHEN answer.duration is not null THEN answer.duration  " +
            "     ELSE 0 " +
            "end answer_duration, " +
            "CASE WHEN hangup.duration is not null THEN hangup.duration  " +
            "     ELSE 0 " +
            "end hangup_duration " +
            "from call_data ring, call_data answer, call_data origin " +
            " left outer join call_data hangup " +
            " on origin.uuid = hangup.uuid  " +
            " and hangup.action = 'HANGUP' " +
            "where origin.action = 'ORIGINATE' " +
            "and origin.uuid = ring.uuid " +
            "and ring.action = 'RING' " +
            "and origin.uuid = answer.uuid " +
            "and answer.action = 'ANSWERED') a", nativeQuery = true)
    Integer getTotalSuccessDurationByTenant(String tenant);

//    My question wasn't clearly answered.
//    Unmatch status has been built on what I understood.
    @Query(value = "SELECT count(o.uuid) FROM call_data o " +
            "WHERE o.tenant = :tenant " +
            "AND o.action = :action " +
            "AND NOT EXISTS " +
            "   (SELECT uuid FROM call_data i " +
            "   WHERE i.uuid = o.uuid " +
            "   AND i.tenant = :tenant " +
            "   AND i.action <> :action )", nativeQuery = true)
    Integer getTotalUnmatchedCalls(String tenant, String action);

}