/**
 * UserMapper interface using mapstruct to map user entity to user details
 * @author Michelle
 */
package com.calendar.login.mappers;

import com.calendar.login.dtos.UserDTO;
import com.calendar.login.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring") //generate mapper impl as a bean
public interface UserMapper {

    /*
      mapping method
     */
    UserDTO toUserDto(User user);
}
