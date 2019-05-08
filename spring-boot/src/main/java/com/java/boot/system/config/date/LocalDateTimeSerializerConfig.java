package com.java.boot.system.config.date;

/**
 * LocalDateTimeSerializerConfig 序列化配置
 *
 * @author xuweizhi
 */
//@Configuration
public class LocalDateTimeSerializerConfig {

    //@Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    //private String pattern;
    //
    ///**
    // * 方案一
    // */
    //@Bean
    //public LocalDateTimeSerializer localDateTimeDeserializer() {
    //    return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    //}
    //
    //@Bean
    //public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    //    return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    //}
    //
    ///**
    // * 方案三
    // */
    //@Bean
    //@Primary
    //public ObjectMapper serializingObjectMapper() {
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    JavaTimeModule javaTimeModule = new JavaTimeModule();
    //    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializers());
    //    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializers());
    //    objectMapper.registerModule(javaTimeModule);
    //    return objectMapper;
    //}
    //
    //public class LocalDateTimeSerializers extends JsonSerializer<LocalDateTime> {
    //    @Override
    //    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
    //            throws IOException {
    //        gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
    //    }
    //}
    //
    //public class LocalDateTimeDeserializers extends JsonDeserializer<LocalDateTime> {
    //    @Override
    //    public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
    //            throws IOException {
    //        return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
    //    }
    //}

}