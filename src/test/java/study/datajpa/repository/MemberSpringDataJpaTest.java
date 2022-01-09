package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.MemberDto;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberSpringDataJpaTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));
        memberRepository.save(new Member("member6",10));
        memberRepository.save(new Member("member7",10));
        // when

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        int age = 10;

        Page<Member> page = memberRepository.findByAge(age,pageRequest);
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername()));

        // then
        List<Member> content = page.getContent();

        content.forEach(System.out::println);
        System.out.println("pageNumber = " + page.getNumber());
        System.out.println("is First?" + page.isFirst());
        System.out.println("has next?" + page.hasNext());
    }
}
