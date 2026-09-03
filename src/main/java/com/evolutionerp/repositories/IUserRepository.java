package com.evolutionerp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.evolutionerp.entities.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    public Users findOneByUsername(String username);

    // BUSCAR POR NOMBRE
    @Query("select count(u.username) from Users u where u.username =:username")
    public int buscarUsername(@Param("username") String nombre);

    // INSERTAR ROLES
    @Transactional
    @Modifying
    @Query(value = "insert into evo.roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
    public void insRol(@Param("rol") String authority, @Param("user_id") Long user_id);

}