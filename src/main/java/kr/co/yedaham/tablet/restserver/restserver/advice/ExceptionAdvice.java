package kr.co.yedaham.tablet.restserver.restserver.advice;

import kr.co.yedaham.tablet.restserver.restserver.advice.exception.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult userNotFound(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CUseridSigninFailedException.class)
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult useridSigninFailed(HttpServletRequest request, CUseridSigninFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("useridSigninFailed.code")), getMessage("useridSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(CCommunicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult communicationException(HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }


    @ExceptionHandler(CTokenExpiredException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult tokenExpiredException(HttpServletRequest request, CTokenExpiredException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("tokenExpired.code")), getMessage("tokenExpired.msg"));
    }

    @ExceptionHandler(CFileNotExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult fileNotExistException(HttpServletRequest request, CFileNotExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("fileNotExist.code")), getMessage("fileNotExist.msg"));
    }

    @ExceptionHandler(CMaxCountOverException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult maxCountOverException(HttpServletRequest request, CMaxCountOverException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("maxCountOver.code")), getMessage("maxCountOver.msg"));
    }

    @ExceptionHandler(CExistingNewCarallocationException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult existingNewCarallocationException(HttpServletRequest request, CExistingNewCarallocationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("existingNewCarallocation.code")), getMessage("existingNewCarallocation.msg"));
    }


    private String getMessage(String code) {
        return getMessage(code, null);
    }
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}