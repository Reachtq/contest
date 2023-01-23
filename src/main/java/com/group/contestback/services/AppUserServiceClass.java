package com.group.contestback.services;

import com.group.contestback.models.AppUser;
import com.group.contestback.models.Groups;
import com.group.contestback.models.Mails;
import com.group.contestback.models.Roles;
import com.group.contestback.repositories.AppUserRepo;
import com.group.contestback.repositories.GroupsRepo;
import com.group.contestback.repositories.RolesRepo;
import com.group.contestback.responseTypes.UserPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServiceClass implements AppUserService, UserDetailsService {
    private final AppUserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final GroupsRepo groupsRepo;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceCS emailServiceCS;
    @Override
    public AppUser saveAppUser(AppUser user) {
        user.setPassHash(passwordEncoder.encode(user.getPassHash()));
        userRepo.save(user);
        userRepo.flush();
        Mails mails = new Mails(user.getEmail(),"Вы были успешно зарегистрированы",new Date());
        emailServiceCS.sendSimpleMessage(mails);
        return user;
    }

    @Override
    public void addRoleToUser(String login, String roleName, String description) throws Exception {
        AppUser user = userRepo.findByLogin(login);
        Roles roles = rolesRepo.findByName(roleName);
        if(user == null) {
            log.error("user not found");
            throw new Exception("user not found");
        } else if (roles == null) {
            log.error("role not found");
            throw new Exception("user not found");
        } else {
            user.setRoleId(roles.getId());
        }
    }

    @Override
    public AppUser getAppUser(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public List<UserPageResponse> getUsers() {
        List<AppUser> users = userRepo.findAll();
        List<Roles> roleNameToId = rolesRepo.findAll();
        List<Groups> groupNameToId = groupsRepo.findAll();
        List<UserPageResponse> usersToSend = new ArrayList<>();
        for(AppUser appUser: users) {
            usersToSend.add((new UserPageResponse(appUser.getId(), appUser.getFirstName(), appUser.getLastName()
                    , appUser.getMiddleName(), appUser.getLogin(), appUser.getEmail(), appUser.getRoleId(),appUser.getGroupId(),
                    roleNameToId.stream().filter(role -> role.getId().equals(appUser.getRoleId()))
                            .findAny()
                            .orElse(new Roles()).getName(),
                    groupNameToId.stream().filter(gr -> gr.getId().equals(appUser.getGroupId()))
                            .findAny().orElse(new Groups()).getNumber())));
        }
        return usersToSend;
    }

    @Override
    public Page<UserPageResponse> getUsersPage(int page, int pageSize) {
        Page<AppUser> entities = userRepo.findAll(PageRequest.of(page, pageSize));
        List<Roles> roleNameToId = rolesRepo.findAll();
        List<Groups> groupNameToId = groupsRepo.findAll();

        Page<UserPageResponse> dtoPage = entities.map(new Function<AppUser, UserPageResponse>() {
            @Override
            public UserPageResponse apply(AppUser entity) {
                UserPageResponse dto = new UserPageResponse(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getMiddleName(),
                        entity.getLogin(),entity.getEmail(),entity.getRoleId(),entity.getGroupId(),
                        roleNameToId.stream().filter(role -> role.getId().equals(entity.getRoleId()))
                                .findAny()
                                .orElse(new Roles()).getName(),
                        groupNameToId.stream().filter(group -> group.getId().equals(entity.getGroupId()))
                                .findAny().orElse(new Groups()).getNumber());
                return dto;
            }
        });
        return dtoPage;
    }

    @Override
    public Page<UserPageResponse> findUsersByLastNamePage(int page, int pageSize, String str) {
        Page<AppUser> entities = userRepo.nameSearch('%' + str + '%', PageRequest.of(page,pageSize));
        List<Roles> roleNameToId = rolesRepo.findAll();
        List<Groups> groupNameToId = groupsRepo.findAll();

        Page<UserPageResponse> dtoPage = entities.map(new Function<AppUser, UserPageResponse>() {
            @Override
            public UserPageResponse apply(AppUser entity) {
                UserPageResponse dto = new UserPageResponse(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getMiddleName(),
                        entity.getLogin(),entity.getEmail(),entity.getRoleId(),entity.getGroupId(),
                        roleNameToId.stream().filter(role -> role.getId().equals(entity.getRoleId()))
                                .findAny()
                                .orElse(new Roles()).getName(),
                        groupNameToId.stream().filter(group -> group.getId().equals(entity.getGroupId()))
                                .findAny().orElse(new Groups()).getNumber());
                return dto;
            }
        });
        return dtoPage;
    }

    @Override
    public UserPageResponse getUserInfo() {
        List<Roles> roleNameToId = rolesRepo.findAll();
        List<Groups> groupNameToId = groupsRepo.findAll();
        AppUser appUser = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        return new UserPageResponse(appUser.getId(), appUser.getFirstName(), appUser.getLastName(), appUser.getMiddleName(), appUser.getLogin(), appUser.getEmail(), appUser.getRoleId(), appUser.getGroupId()
        return  (new UserPageResponse(appUser.getId(), appUser.getFirstName(), appUser.getLastName()
                , appUser.getMiddleName(), appUser.getLogin(), appUser.getEmail(), appUser.getRoleId(),appUser.getGroupId(),
                roleNameToId.stream().filter(role -> role.getId().equals(appUser.getRoleId()))
                        .findAny()
                        .orElse(new Roles()).getName(),
                groupNameToId.stream().filter(gr -> gr.getId().equals(appUser.getGroupId()))
                        .findAny().orElse(new Groups()).getNumber())) ;
    }

    @Override
    public void addEmailToUser(String login, String email) {
        AppUser user = userRepo.findByLogin(login);
        Mails mail1 = new Mails(user.getEmail(), "Изменение почты\",\"Ваша почта была изменена на " + email, new Date());
        emailServiceCS.sendSimpleMessage(mail1);
        user.setEmail(email);
        Mails mail2 = new Mails(user.getEmail(), "Изменение почты\",\"Ваша почта была изменена на " + email, new Date());
        emailServiceCS.sendSimpleMessage(mail2);
    }

    @Override
    public void setUserGroup(String login, Integer id) {
        AppUser user = userRepo.findByLogin(login);
        Optional<Groups> groups = groupsRepo.findById(id);
        if(user == null) {
            log.error("user not found");
            throw new RuntimeException("user not found");
        } else if(groups.isEmpty()) {
            log.error("group not found");
            throw new RuntimeException("group not found");
        } else {
            user.setGroupId(groups.get().getId());
        }
    }

    @Override
    public void resetPassword(String login, String newPassword) {
        AppUser user = userRepo.findByLogin(login);
        user.setPassHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AppUser user = userRepo.findByLogin(login);
        if(user == null) {
            log.error("user not found");
            throw new RuntimeException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        rolesRepo.findAllById(user.getRoleId()).forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getLogin(), user.getPassHash(), authorities);
    }
}
