package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository sut;

    @BeforeEach
    void setUp() {
        sut.deleteAll();
    }

    @Test
    public void testAddUser() {
        //arrange
        UserModel user = new UserModel().builder().email("test@test.test").name("test").build();

        //act
        var savedUser = sut.save(user);

        //asser
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getName(), "test");
        Assertions.assertEquals(savedUser.getEmail(), "test@test.test");
    }

    @Test
    public void testGetAllUsers() {
        //arrange
        UserModel user = new UserModel().builder().email("test@test.test").name("test").build();
        UserModel user1 = new UserModel().builder().email("test1@test.test").name("test1").build();
        sut.save(user);
        sut.save(user1);

        //act
        var users = sut.findAll();

        //asser
        Assertions.assertNotNull(users);
        Assertions.assertEquals(users.size(), 2);
    }

    @Test
    public void testGetUserById() {
        //arrange
        UserModel user = new UserModel().builder().email("test@test.test").name("test").build();

        //act
        var savedUser = sut.save(user);

        //asser
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getName(), "test");
        Assertions.assertEquals(savedUser.getEmail(), "test@test.test");
    }

    @Test
    public void testGetUserByEmail() {
        //arrange
        UserModel user = new UserModel().builder().email("test@test.test").name("test").build();
        UserModel user1 = new UserModel().builder().email("test1@test.test").name("test1").build();
        sut.save(user);
        sut.save(user1);

        //act
        var savedUser = sut.findByEmail("test@test.test");

        //asser
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.get().getName(), "test");
        Assertions.assertEquals(savedUser.get().getEmail(), "test@test.test");
    }


    @Test
    public void testDeleteUser() {
        //arrange
        UserModel user = new UserModel().builder().email("test@test.test").name("test").build();
        UserModel user1 = new UserModel().builder().email("test1@test.test").name("test1").build();
        var savedUser = sut.save(user);
        sut.save(user1);

        //act
        sut.deleteById(savedUser.getId());
        var result = sut.findById(savedUser.getId());

        //asser
        Assertions.assertEquals(result.isEmpty(), true);
    }
}
