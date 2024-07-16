/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.co.id.emsserverside.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author mystogan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventDto {
    
    private Long userId;
    
    private Long eventId;
    
    private Long registrationStatusId;
    
}
