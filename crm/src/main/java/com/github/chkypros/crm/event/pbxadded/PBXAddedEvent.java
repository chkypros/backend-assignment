package com.github.chkypros.crm.event.pbxadded;

import com.github.chkypros.crm.domain.PBX;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PBXAddedEvent {
    private PBX pbx;
}
