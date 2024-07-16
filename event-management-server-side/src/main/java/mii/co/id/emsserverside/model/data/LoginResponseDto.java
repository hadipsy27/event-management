/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.co.id.emsserverside.model.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Richo Dea
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private List<String> authorities;
    private String username;

}
