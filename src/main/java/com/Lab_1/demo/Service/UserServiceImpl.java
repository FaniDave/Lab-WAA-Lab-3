package com.Lab_1.demo.Service;

import com.Lab_1.demo.Aspect.ExecutionTime;
import com.Lab_1.demo.Entity.Request.UserDtoReq;
import com.Lab_1.demo.Entity.Response.PostDtoRes;
import com.Lab_1.demo.Entity.Response.UserDtoRes;
import com.Lab_1.demo.Entity.User;
import com.Lab_1.demo.Repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepo userRepo;

    @Override
    public List<UserDtoRes> findAllUsers() {
        return userRepo.findAll().stream().map(user -> modelMapper.map(user, UserDtoRes.class)).toList();
    }

    @Override
    @ExecutionTime
    public UserDtoRes findUserById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        return modelMapper.map(user, UserDtoRes.class);
    }

    @Override
    public UserDtoRes saveUser(UserDtoReq userDtoReq) {
        User userToBeSaved = modelMapper.map(userDtoReq, User.class);
        userRepo.save(userToBeSaved);
        return modelMapper.map(userToBeSaved, UserDtoRes.class);
    }

    @Override
    public List<PostDtoRes> findPostsByUserId(Long id) {
        User user = userRepo.findById(id).orElse(null);
        return user.getPosts().stream().map(post -> modelMapper.map(post, PostDtoRes.class)).toList();
    }

    @Override
    public List<UserDtoRes> findUsersWithMoreThanNPosts(int n) { // Override this method
        return userRepo.findUsersWithMoreThanNPosts(n).stream().map(user -> modelMapper.map(user, UserDtoRes.class)).toList();
    }
}
