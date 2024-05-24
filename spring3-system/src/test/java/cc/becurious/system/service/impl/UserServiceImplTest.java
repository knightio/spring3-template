package cc.becurious.system.service.impl;

import cc.becurious.system.domain.SysUser;
import cc.becurious.system.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

    @Mock
    private UserMapper mockUserMapper;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }


    @Test
    void testSelectUserById() {
        // Setup
        final SysUser expectedResult = SysUser.builder().build();
        when(mockUserMapper.selectUserById(0L)).thenReturn(SysUser.builder().build());

        // Run the test
        final SysUser result = userServiceImplUnderTest.selectUserById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSelectUserByUserName() {
        // Setup
        final SysUser expectedResult = SysUser.builder().build();
        when(mockUserMapper.selectUserByUserName("userName")).thenReturn(SysUser.builder().build());

        // Run the test
        final SysUser result = userServiceImplUnderTest.selectUserByUserName("userName");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateUser() {
        // Setup
        final SysUser sysUser = SysUser.builder().build();
        when(mockUserMapper.updateUser(SysUser.builder().build())).thenReturn(0L);

        // Run the test
        final Long result = userServiceImplUnderTest.updateUser(sysUser);

        // Verify the results
        assertEquals(0L, result);

    }
}
