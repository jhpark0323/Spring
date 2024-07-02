package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// class이름 (MemberService)에 커서를 대고 ctrl + shift + t
// -> 자동으로 test파일 만들어줌
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member);

        // memberRepository.findByName(member.getName()) 작성후
        //  -> 단축키 ctrl + alt + v
        // -> 앞에 Optinal<Member> byName이 뜸
//        Optional<Member> byName = memberRepository.findByName(member.getName());

        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        // ctrl + alt + m 하면 드래그한 내용을 새로운 함수로 만들어줌
        // 함수 안에 또 다른 함수가 있을 때 얘를 밖으로 꺼내고 싶을 때 사용
        // -> Extract Method 방법
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
     전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
