package dev.gunlog.multi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRedisRepository extends CrudRepository<MemberInfoRedis, Long> {
}