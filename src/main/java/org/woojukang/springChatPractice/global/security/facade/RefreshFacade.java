package org.woojukang.springChatPractice.global.security.facade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.woojukang.springChatPractice.global.config.exception.BaseExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;
import org.woojukang.springChatPractice.global.security.dto.response.ReissueResponse;
import org.woojukang.springChatPractice.global.security.service.RefreshService;

@Component
@RequiredArgsConstructor
public class RefreshFacade {

    private final RefreshService refreshService;

    public ReissueResponse reissue(HttpServletRequest request){

        ReissueResponse response = refreshService.refreshCookies(request);

        return switch (response.status()) {

            case REFRESH_EXPIRED ->
                    throw new BaseException(BaseExceptionEnum.REFRESH_TOKEN_NOT_FOUND);

            case REFRESH_NULL ->
                    throw new BaseException(BaseExceptionEnum.REFRESH_TOKEN_EXPIRED);

            default ->
                    response;
        };

    }
}
