//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//
package at.smartlab.kids.paint;

public interface ToolCmdListener {
	
	public enum CMD {
		Clear,
        Share,
        Save
	}

	void trigger(CMD c);
	
}
