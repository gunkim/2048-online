package io.github.gunkim.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class JasyptConfig(
    @Value("\${jasypt.encryptor.password}")
    private val jasyptPassword: String,
) {
    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor = PooledPBEStringEncryptor().apply {
        setConfig(
            SimpleStringPBEConfig().apply {
                password = jasyptPassword
                algorithm = "PBEWithMD5AndDES"
                setKeyObtentionIterations("1000")
                setPoolSize("1")
                providerName = "SunJCE"
                setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
                setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator")
                stringOutputType = "base64"
            },
        )
    }
}
