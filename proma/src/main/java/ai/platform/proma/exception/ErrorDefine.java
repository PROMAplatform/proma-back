package ai.platform.proma.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorDefine {

    // Bad Request
    INVALID_ARGUMENT("4000", HttpStatus.BAD_REQUEST, "Bad Request: Invalid Arguments"),
    INVALID_PROMPT_CATEGORY("4001", HttpStatus.BAD_REQUEST, "Bad Request: Invalid PromptCategory"),
    INVALID_PROMPT_METHOD("4002", HttpStatus.BAD_REQUEST, "Bad Request: Invalid PromptMethod"),
    INVALID_BLOCK_CATEGORY("4003", HttpStatus.BAD_REQUEST, "Bad Request: Invalid BlockCategory"),
    INVALID_BLOCK_METHOD("4004", HttpStatus.BAD_REQUEST, "Bad Request: Invalid BlockMethod"),
    INVALID_LIKE_ORDER("4005", HttpStatus.BAD_REQUEST,"Bad Request: Invalid like parameter"),
    INVALID_HEADER_ERROR("4006", HttpStatus.BAD_REQUEST, "Bad Request: Invalid Header Error"),

    NULL_POST_ERROR("4007", HttpStatus.BAD_REQUEST, "Bad Request: Post cannot be null "),

    // Not Found
    USER_NOT_FOUND("4040", HttpStatus.NOT_FOUND, "Not Found: User Not Found"),
    COMMUNICATION_METHOD_NOT_FOUND("4041", HttpStatus.NOT_FOUND, "Not Found: Communication Method Not Found"),
    POST_NOT_FOUND("4042", HttpStatus.NOT_FOUND, "Not Found: Post Not Found"),
    PROMPT_NOT_FOUND("4043", HttpStatus.NOT_FOUND, "Not Found: Prompt Not Found"),
    CHAT_ROOM_NOT_FOUND("4044", HttpStatus.NOT_FOUND, "Not Found: Chat Room Not Found"),
    BLOCK_NOT_FOUND("4045", HttpStatus.NOT_FOUND, "Not Found: Block Not Found"),
    MESSAGE_NOT_FOUND("4046", HttpStatus.NOT_FOUND, "Not Found: Message Not Found"),
    PROMPT_BLOCK_NOT_FOUND("4047", HttpStatus.NOT_FOUND, "Not Found: Prompt Block Not Found"),

    //Forbidden
    UNAUTHORIZED_USER("4030", HttpStatus.FORBIDDEN, "Forbidden: Unauthorized User"),
    BLOCK_NOT_MATCH("4038", HttpStatus.FORBIDDEN, "Forbidden: Block Not Match"),


    // 소셜로그인 관련
    LOGIN_ACCESS_DENIED("4031", HttpStatus.FORBIDDEN, "Forbidden: Login Access Denied"),
    TOKEN_MALFORMED("4032", HttpStatus.FORBIDDEN, "Forbidden: Token Malformed"),
    TOKEN_TYPE("4033", HttpStatus.FORBIDDEN, "Forbidden: Token Type"),
    TOKEN_EXPIRED("4034", HttpStatus.FORBIDDEN, "Forbidden: Token Expired"),
    TOKEN_UNSUPPORTED("4035", HttpStatus.FORBIDDEN, "Forbidden: Token Unsupported"),
    TOKEN_UNKNOWN("4036", HttpStatus.FORBIDDEN, "Forbidden: Token Unknown"),
    TOKEN_INVALID("4037", HttpStatus.FORBIDDEN, "Forbidden: Token Invalid"),


    BLOCK_NOT_DELETE("4050", HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed: Block Not Delete");


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
