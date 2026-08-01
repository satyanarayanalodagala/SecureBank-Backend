package com.securebank.security;


import java.util.Collections;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.securebank.entity.Admin;
import com.securebank.entity.Customer;
import com.securebank.enums.Role;
import com.securebank.repository.AdminRepository;
import com.securebank.repository.CustomerRepository;



@Service
public class CustomUserDetailsService 
        implements UserDetailsService {




    private final CustomerRepository customerRepository;


    private final AdminRepository adminRepository;






    public CustomUserDetailsService(

            CustomerRepository customerRepository,

            AdminRepository adminRepository

    ) {


        this.customerRepository = customerRepository;


        this.adminRepository = adminRepository;


    }









    @Override
    public UserDetails loadUserByUsername(String email)

            throws UsernameNotFoundException {




        if(email == null || email.isBlank()) {


            throw new UsernameNotFoundException(

                    "Email cannot be empty"

            );


        }









        // ==================================================
        //                  ADMIN LOGIN FIRST
        // ==================================================



        Admin admin =

                adminRepository

                .findByEmail(email)

                .orElse(null);





        if(admin != null) {



            String role =

                    admin.getRole() == null

                    ? Role.ADMIN.name()

                    : admin.getRole().replace(
                            "ROLE_",
                            ""
                      );







            UserDetails user =

                    User.builder()



                    .username(

                            admin.getEmail()

                    )



                    .password(

                            admin.getPassword()

                    )



                    .authorities(



                            Collections.singletonList(



                                    new SimpleGrantedAuthority(

                                            "ROLE_" + role

                                    )



                            )



                    )



                    .build();








            System.out.println(

                    "========== ADMIN AUTHORITIES =========="

            );



            System.out.println(

                    user.getAuthorities()

            );



            System.out.println(

                    "ADMIN EMAIL : "

                    + admin.getEmail()

            );



            System.out.println(

                    "ADMIN ROLE FROM DB : "

                    + admin.getRole()

            );



            System.out.println(

                    "FINAL AUTHORITY : "

                    + user.getAuthorities()

            );







            return user;


        }














        // ==================================================
        //                 CUSTOMER LOGIN
        // ==================================================




        Customer customer =

                customerRepository

                .findByEmail(email)

                .orElse(null);







        if(customer != null) {



            String role =

                    customer.getRole() == null

                    ? Role.CUSTOMER.name()

                    : customer.getRole().name();







            UserDetails user =

                    User.builder()



                    .username(

                            customer.getEmail()

                    )



                    .password(

                            customer.getPassword()

                    )



                    .authorities(



                            Collections.singletonList(



                                    new SimpleGrantedAuthority(

                                            "ROLE_" + role

                                    )



                            )



                    )



                    .build();








            System.out.println(

                    "========== CUSTOMER AUTHORITIES =========="

            );



            System.out.println(

                    user.getAuthorities()

            );







            return user;



        }












        throw new UsernameNotFoundException(

                "User not found with email : " + email

        );



    }



}