package carbon.sdk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @date 2022/9/19 9:47
 */
@AllArgsConstructor
@Getter
public enum ContractLanguageTypeEnum {
    /**
     * 默认
     */
    DEFAULT(0, "默认"),
    /**
     * go
     */
    TINY_GO(1, "go"),

    /**
     * rust
     */
    RUST(2, "rust"),

    /**
     * vm-docker, language-golang
     */
    DOCKER_GO(3, "docker_go");

    final Integer type;

    final String msg;
}
