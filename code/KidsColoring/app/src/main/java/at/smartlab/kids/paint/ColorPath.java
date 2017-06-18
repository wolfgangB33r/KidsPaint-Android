//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//

package at.smartlab.kids.paint;

import android.graphics.Paint;
import android.graphics.Path;

public class ColorPath {
	public Paint paint;
	public Path path;
	
	public ColorPath(Path p, Paint paint) {
		this.paint = paint;
		this.path = p;
	}
	
}
