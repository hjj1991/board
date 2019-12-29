package board.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import board.member.entity.MemberEntity;

@Repository
public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByUserId(String userId);

	List<MemberEntity> findAllByOrderByUserIdAsc();
	
	boolean existsByUserId(String userId);
	
}