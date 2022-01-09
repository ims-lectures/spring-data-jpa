package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.MemberDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    void testMember() {
        // given
        Member member = new Member("memberA");
        Member save = memberJpaRepository.save(member);
        // when
        Member findMember = memberJpaRepository.find(save.getId());
        // then

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void jpaTest(){
        Member member = new Member("memberB");
        Member save = memberRepository.save(member);
        Optional<Member> byId =
                memberRepository.findById(save.getId());

        Member findMember = byId.get();

        assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void printMemberRepository(){
        System.out.println("memberRepository = " + memberRepository.getClass());
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        // given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        Member m3 = new Member("AAA",30);
        // when

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);

        List<Member> aaa = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        aaa.forEach(System.out::println);
        // then
    }

    @Test
    void testQuery() {
        // given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        Member m3 = new Member("AAA",30);
        // when

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);
        // when

        List<Member> aaa = memberRepository.findUser("AAA", 10);
        // then
        System.out.println(aaa.get(0));
    }

    @Test
    void findUsernameList() {
        // given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);
        Member m3 = new Member("CCC",30);
        // when
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);
        // then
        List<String> usernameList = memberRepository.findUsernameList();
        usernameList.forEach(System.out::println);
    }

    @Test
    void findMemberDto() {
        // given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);
        Member m3 = new Member("CCC",30);
        // when
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        memberDto.forEach(System.out::println);
        // then
    }

    @Test
    void paging() {
        // given
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",10));
        memberJpaRepository.save(new Member("member3",10));
        memberJpaRepository.save(new Member("member4",10));
        memberJpaRepository.save(new Member("member5",10));
        memberJpaRepository.save(new Member("member6",10));
        memberJpaRepository.save(new Member("member7",10));
        // when

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // then
        members.forEach(System.out::println);
        System.out.println("totalCount = " + totalCount);
    }
}