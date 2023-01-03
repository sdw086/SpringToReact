package com.example.reactboot.modules.member.service;

import com.example.reactboot.common.pagination.PaginationInfo;
import com.example.reactboot.common.utils.*;
import com.example.reactboot.login.vo.UserVo;
import com.example.reactboot.modules.common.file.service.FileService;
import com.example.reactboot.modules.member.dto.MemberDto;
import com.example.reactboot.modules.member.dto.MemberJoinDto;
import com.example.reactboot.modules.member.dto.ProfileUploadDto;
import com.example.reactboot.modules.member.mapper.MemberMapper;
import com.example.reactboot.modules.member.vo.MemberListVo;
import com.example.reactboot.modules.member.vo.MemberLoginVo;
import com.example.reactboot.modules.member.vo.MemberVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class MemberService {

    @Value("${api.token.typ}")
    private String typ;
    @Value("${api.token.alg}")
    private String alg;
    @Value("${api.token.api-key}")
    private String apiKey;
    @Value("${api.token.secret-key}")
    private String secretKey;
    @Value("${api.token.issuer}")
    private String issuer;
    @Value("${api.token.expire}")
    private long expire;
    @Value("${api.token.refresh-expire}")
    private long expireOneDay;
    @Value(("${api.token.student-url}"))
    private String studentUrl;
    @Value(("${api.token.parents-url}"))
    private String parentsUrl;

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final PropertyUtil propertyUtil;

    JwtTokenUtil jwtTokenUtil   = new JwtTokenUtil();

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, PropertyUtil propertyUtil) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.propertyUtil = propertyUtil;
    }

    /**
     * 로그인
     */
    public MemberLoginVo selectMemberLogin(HttpServletRequest request, HttpServletResponse response, MemberDto memberDto) throws Exception {
        String token                        = StringUtil.nvl(request.getHeader("Authorization"), "");
        String refreshToken                 = StringUtil.nvl(request.getHeader("refreshToken"), "");

        UserVo userVo = memberMapper.selectMemberLogin(memberDto.getUsername());
        ModelMapper modelMapper             = new ModelMapper();
        MemberLoginVo memberLoginVo         = new MemberLoginVo();

        if(userVo != null && passwordEncoder.matches(AES128Crypto.decrypt(memberDto.getPwd()), userVo.getPwd()) == true) {
            memberLoginVo = modelMapper.map(userVo, MemberLoginVo.class);
            memberLoginVo.setImg("fileUrl".concat(userVo.getImg()));

            Map<String, Object> tokenMap    = new HashMap<>();

            tokenMap.put("typ"              , typ);
            tokenMap.put("alg"              , alg);
            tokenMap.put("apiKey"           , apiKey);
            tokenMap.put("secretKey"        , secretKey);
            tokenMap.put("expire"           , expire);
            tokenMap.put("expireOneDay"     , expireOneDay);
            tokenMap.put("issuer"           , issuer);

            if ("".equals(token)) {
                token          = jwtTokenUtil.createToken(propertyUtil, memberLoginVo.memberId);
                refreshToken   = jwtTokenUtil.refreshToken(propertyUtil, memberLoginVo.memberId);
            } else {

                if (Pattern.matches("Bearer .&", token)) {
                    token   = token.replaceAll("^Bearer( )*", token);
                    Map<String, Object> jwtClaims   = jwtTokenUtil.verifyJWT(secretKey, token);

                    if ("exp".equals(jwtClaims.get("result"))) {    // token 만료시 재발행
                        token          = jwtTokenUtil.createToken(propertyUtil, memberLoginVo.memberId);
                        refreshToken   = jwtTokenUtil.refreshToken(propertyUtil, memberLoginVo.memberId);
                    }
                }
            }

            if ("ST".equals(memberDto.getMemberDiv())) {
                response.setHeader("Access-Control-Allow-Origin", studentUrl);
            } else {
                response.setHeader("Access-Control-Allow-Origin", parentsUrl);
            }

            response.setHeader("Access-Control-Allow-Methods","GET, POST");
            response.setHeader("access-Control-Expose-Headers", "Authorization, RefreshToken");
            response.setHeader("Authorization"  , token);
            response.setHeader("RefreshToken"   , refreshToken);

            // last login & refresh token 저장
            if (!"".equals(token)) {
                String memberIp = request.getRemoteAddr();
                memberMapper.updateLastLogin(memberDto.getUsername(), memberIp, refreshToken);
            }
        }

        return memberLoginVo;
    }

    /**
     * refresh token 확인
     */
    public String selectRefreshToken(String refreshToken) {
        return memberMapper.selectRefreshToken(refreshToken);
    }

    /**
     * 로그아웃
     */
    public int updateMemberLogout(MemberDto memberDto) {
        return memberMapper.updateMemberLogout(memberDto);
    }

    /**
     * 회원 목록
     */
    public MemberListVo selectMemberList(MemberDto memberDto) {
        MemberListVo memberListVo           = new MemberListVo();
        PaginationInfo paginationInfo       = new PaginationInfo(memberDto.getCurrentPageNo(), memberDto.getRecordCountPerPage(), memberDto.getPaginationSize());

        memberDto.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        memberDto.setLastRecordIndex(paginationInfo.getLastRecordIndex());

        int totalCount                      = 0;
        int dataListCount                   = 0;
        List<MemberVo> selectMemberList     = memberMapper.selectMemberList(memberDto);

        if (!CollectionUtils.isEmpty(selectMemberList)) {
            totalCount                      = selectMemberList.get(0).getTotalCount();
            dataListCount                   = selectMemberList.size();
        }

        paginationInfo.setTotalRecordCount(totalCount);

        // 반환 데이터 만들기
        memberListVo.setSearchField(memberDto.getSearchField());
        memberListVo.setSearchWord(memberDto.getSearchWord());
        memberListVo.setCurrentPageNo(memberDto.getCurrentPageNo());
        memberListVo.setMemberDiv(memberDto.getMemberDiv());
        memberListVo.setTotalCount(totalCount);
        memberListVo.setDataListCount(dataListCount);
        memberListVo.setList(selectMemberList);

        return memberListVo;
    }

    /**
     * 회원 가입
     */
    public int insertMember(MemberJoinDto memberJoinDto) {
        int resultCode                  = 1;

        if (!memberJoinDto.getPwd1().equals(memberJoinDto.getPwd2()) || "".equals(memberJoinDto.getPwd1())) {
            resultCode                  = -1;
        } else {
            // 아이디 중복 확인
            String memberDuplicateId    = selectMemberDuplicate(memberJoinDto.getMemberId());

            if (!"".equals(memberDuplicateId)) {
                resultCode              = -2;
            }

            if (resultCode > 0) {
                memberJoinDto.setPwd(passwordEncoder.encode(memberJoinDto.getPwd1()));

                resultCode = memberMapper.insertMember(memberJoinDto);
            }
        }

        return resultCode;
    }

    /**
     * 프로필 사진 업로드
     */
    public ProfileUploadDto uploadProfile(MultipartFile file, String thumbnailYn) {
        ProfileUploadDto profileUploadDto   = new ProfileUploadDto();

        FileService fileService             = new FileService(propertyUtil);
        Map<String, Object> fileData        = fileService.uploadFile(file, "MEMBER", thumbnailYn);

        if (fileData != null) {
            profileUploadDto.setImg(fileData.get("filePath").toString());
            profileUploadDto.setFileUrl(fileData.get("fileUrl").toString());
        } else {
            profileUploadDto.setImg("");
        }

        return profileUploadDto;
    }

    /**
     * 아이디, 닉네임 중복 확인
     */
    public String selectMemberDuplicate(String memberId) {
        return memberMapper.selectMemberDuplicate(memberId);
    }
}
