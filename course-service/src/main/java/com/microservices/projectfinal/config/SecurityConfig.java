package com.microservices.projectfinal.config;

//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    private final AuthorDetailsService authorDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement(
//                        sessionManagement -> sessionManagement
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2ResourceServer ->
//                        oauth2ResourceServer
//                                .jwt(jwt -> jwt
//                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
//                                )
//                );
//        return http.build();
//    }
//
//    @Bean
//    Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
//        return new JwtConverter(authorDetailsService);
//    }
}
