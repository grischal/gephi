/*
 Copyright 2008-2010 Gephi
 Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
 Website : http://www.gephi.org

 This file is part of Gephi.

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2011 Gephi Consortium. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s):

 Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.visualization.model.node;

import static com.jogamp.opengl.GL.GL_LINES;
import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.GL_POLYGON;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import org.gephi.graph.api.Node;
import org.gephi.visualization.model.Model;
import org.gephi.visualization.model.Modeler;
import org.gephi.visualization.opengl.CompatibilityEngine;

/**
 *
 * @author Mathieu Bastian
 */
public class NodeModeler extends Modeler {

    public int SHAPE_BLUE_DISK16;
    public int SHAPE_BLUE_DISK32;
    public int SHAPE_BLUE_DISK64;
    public int SHAPE_PURPLE_DISK16;
    public int SHAPE_PURPLE_DISK32;
    public int SHAPE_PURPLE_DISK64;
    public int SHAPE_ORANGE_DISK16;
    public int SHAPE_ORANGE_DISK32;
    public int SHAPE_ORANGE_DISK64;
    public int SHAPE_CYAN_DISK16;
    public int SHAPE_CYAN_DISK32;
    public int SHAPE_CYAN_DISK64;
    
    public int SHAPE_SELECT_BLUE_DISK16;
    public int SHAPE_SELECT_BLUE_DISK32;
    public int SHAPE_SELECT_BLUE_DISK64;
    public int SHAPE_SELECT_PURPLE_DISK16;
    public int SHAPE_SELECT_PURPLE_DISK32;
    public int SHAPE_SELECT_PURPLE_DISK64;
    public int SHAPE_SELECT_ORANGE_DISK16;
    public int SHAPE_SELECT_ORANGE_DISK32;
    public int SHAPE_SELECT_ORANGE_DISK64;
    public int SHAPE_SELECT_CYAN_DISK16;
    public int SHAPE_SELECT_CYAN_DISK32;
    public int SHAPE_SELECT_CYAN_DISK64;

    public NodeModeler(CompatibilityEngine engine) {
        super(engine);
    }

    public NodeModel initModel(Node n) {
        NodeDiskModel obj = new NodeDiskModel((Node) n);
        obj.modelBlueType = SHAPE_BLUE_DISK64;
        obj.modelPurpleType = SHAPE_PURPLE_DISK64;
        obj.modelOrangeType = SHAPE_ORANGE_DISK64;
        obj.modelCyanType = SHAPE_CYAN_DISK64;
        
        obj.modelSelectBlueType = SHAPE_SELECT_BLUE_DISK64;
        obj.modelSelectPurpleType = SHAPE_SELECT_PURPLE_DISK64;
        obj.modelSelectOrangeType = SHAPE_SELECT_ORANGE_DISK64;
        obj.modelSelectCyanType = SHAPE_SELECT_CYAN_DISK64;

        chooseModel(obj);
        return obj;
    }

    @Override
    public void chooseModel(Model object3d) {
        NodeDiskModel obj = (NodeDiskModel) object3d;
        if (config.isDisableLOD()) {
            obj.modelBlueType = SHAPE_BLUE_DISK64;
            obj.modelPurpleType = SHAPE_PURPLE_DISK64;
            obj.modelOrangeType = SHAPE_ORANGE_DISK64;
            obj.modelCyanType = SHAPE_CYAN_DISK64;
            
            obj.modelSelectBlueType = SHAPE_SELECT_BLUE_DISK64;
            obj.modelSelectPurpleType = SHAPE_SELECT_PURPLE_DISK64;
            obj.modelSelectOrangeType = SHAPE_SELECT_ORANGE_DISK64;
            obj.modelSelectCyanType = SHAPE_SELECT_CYAN_DISK64;
            return;
        }

        float distance = cameraDistance(obj) / (obj.getNode().size() * drawable.getGlobalScale());
        if (distance > 50) {
            obj.modelBlueType = SHAPE_BLUE_DISK16;
            obj.modelPurpleType = SHAPE_PURPLE_DISK16;
            obj.modelOrangeType = SHAPE_ORANGE_DISK16;
            obj.modelCyanType = SHAPE_CYAN_DISK16;

            obj.modelSelectBlueType = SHAPE_SELECT_BLUE_DISK16;
            obj.modelSelectPurpleType = SHAPE_SELECT_PURPLE_DISK16;
            obj.modelSelectOrangeType = SHAPE_SELECT_ORANGE_DISK16;
            obj.modelSelectCyanType = SHAPE_SELECT_CYAN_DISK16;
        } else {
            obj.modelBlueType = SHAPE_BLUE_DISK32;
            obj.modelPurpleType = SHAPE_PURPLE_DISK32;
            obj.modelOrangeType = SHAPE_ORANGE_DISK32;
            obj.modelCyanType = SHAPE_CYAN_DISK32;

            obj.modelSelectBlueType = SHAPE_SELECT_BLUE_DISK32;
            obj.modelSelectPurpleType = SHAPE_SELECT_PURPLE_DISK32;
            obj.modelSelectOrangeType = SHAPE_SELECT_ORANGE_DISK32;
            obj.modelSelectCyanType = SHAPE_SELECT_CYAN_DISK32;
        }
    }

    @Override
    public int initDisplayLists(GL2 gl, GLU glu, GLUquadric quadric, int ptr) {

        //Blue Disk16
        SHAPE_BLUE_DISK16 = ptr + 1;
        gl.glNewList(SHAPE_BLUE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);        
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //Blue Disk32
        SHAPE_BLUE_DISK32 = SHAPE_BLUE_DISK16 + 1;
        gl.glNewList(SHAPE_BLUE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //Blue Disk64
        SHAPE_BLUE_DISK64 = SHAPE_BLUE_DISK32 + 1;
        gl.glNewList(SHAPE_BLUE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //PURPLE Disk16
        SHAPE_PURPLE_DISK16 = SHAPE_BLUE_DISK64 + 1;
        gl.glNewList(SHAPE_PURPLE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);        
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //PURPLE Disk32
        SHAPE_PURPLE_DISK32 = SHAPE_PURPLE_DISK16 + 1;
        gl.glNewList(SHAPE_PURPLE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //PURPLE Disk64
        SHAPE_PURPLE_DISK64 = SHAPE_PURPLE_DISK32 + 1;
        gl.glNewList(SHAPE_PURPLE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();
        
        //ORANGE Disk16
        SHAPE_ORANGE_DISK16 = SHAPE_PURPLE_DISK64 + 1;
        gl.glNewList(SHAPE_ORANGE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);        
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //ORANGE Disk32
        SHAPE_ORANGE_DISK32 = SHAPE_ORANGE_DISK16 + 1;
        gl.glNewList(SHAPE_ORANGE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //ORANGE Disk64
        SHAPE_ORANGE_DISK64 = SHAPE_ORANGE_DISK32 + 1;
        gl.glNewList(SHAPE_ORANGE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();
        
        //CYAN Disk16
        SHAPE_CYAN_DISK16 = SHAPE_ORANGE_DISK64 + 1;
        gl.glNewList(SHAPE_CYAN_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);        
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //CYAN Disk32
        SHAPE_CYAN_DISK32 = SHAPE_CYAN_DISK16 + 1;
        gl.glNewList(SHAPE_CYAN_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //CYAN Disk64
        SHAPE_CYAN_DISK64 = SHAPE_CYAN_DISK32 + 1;
        gl.glNewList(SHAPE_CYAN_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //***************
        //SELECTED SHAPES
        //***************
        //Blue Disk16
        SHAPE_SELECT_BLUE_DISK16 = SHAPE_CYAN_DISK64 + 1;
        gl.glNewList(SHAPE_SELECT_BLUE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //Blue Disk32
        SHAPE_SELECT_BLUE_DISK32 = SHAPE_SELECT_BLUE_DISK16 + 1;
        gl.glNewList(SHAPE_SELECT_BLUE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //Blue Disk64
        SHAPE_SELECT_BLUE_DISK64 = SHAPE_SELECT_BLUE_DISK32 + 1;
        gl.glNewList(SHAPE_SELECT_BLUE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.651f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //PURPLE Disk16
        SHAPE_SELECT_PURPLE_DISK16 = SHAPE_SELECT_BLUE_DISK64 + 1;
        gl.glNewList(SHAPE_SELECT_PURPLE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);    
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //PURPLE Disk32
        SHAPE_SELECT_PURPLE_DISK32 = SHAPE_SELECT_PURPLE_DISK16 + 1;
        gl.glNewList(SHAPE_SELECT_PURPLE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //PURPLE Disk64
        SHAPE_SELECT_PURPLE_DISK64 = SHAPE_SELECT_PURPLE_DISK32 + 1;
        gl.glNewList(SHAPE_SELECT_PURPLE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.737f, 0.541f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();
        
        //ORANGE Disk16
        SHAPE_SELECT_ORANGE_DISK16 = SHAPE_SELECT_PURPLE_DISK64 + 1;
        gl.glNewList(SHAPE_SELECT_ORANGE_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
       gl.glColor3f(0.749f, 0.925f, 1.0f); 
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //ORANGE Disk32
        SHAPE_SELECT_ORANGE_DISK32 = SHAPE_SELECT_ORANGE_DISK16 + 1;
        gl.glNewList(SHAPE_SELECT_ORANGE_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //ORANGE Disk64
        SHAPE_SELECT_ORANGE_DISK64 = SHAPE_SELECT_ORANGE_DISK32 + 1;
        gl.glNewList(SHAPE_SELECT_ORANGE_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.902f, 0.667f, 0.541f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();
        
        //CYAN Disk16
        SHAPE_SELECT_CYAN_DISK16 = SHAPE_SELECT_ORANGE_DISK64 + 1;
        gl.glNewList(SHAPE_SELECT_CYAN_DISK16, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);      
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
//        glu.gluDisk(quadric, 0, 0.5, 6, 1);
        gl.glEndList();
        //Fin

        //CYAN Disk32
        SHAPE_SELECT_CYAN_DISK32 = SHAPE_SELECT_CYAN_DISK16 + 1;
        gl.glNewList(SHAPE_SELECT_CYAN_DISK32, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, -180);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 12, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 12, 1, 0, 180);
//        glu.gluDisk(quadric, 0, 0.5, 12, 2);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        //CYAN Disk64
        SHAPE_SELECT_CYAN_DISK64 = SHAPE_SELECT_CYAN_DISK32 + 1;
        gl.glNewList(SHAPE_SELECT_CYAN_DISK64, GL2.GL_COMPILE);
        gl.glTranslatef(-6.25f,0.0f,0.0f);
        gl.glColor3f(0.541f, 0.804f, 0.902f);
        glu.gluDisk(quadric, 0, 2.5, 24, 1);
        gl.glColor3f(0.749f, 0.925f, 1.0f);
        gl.glBegin(GL_POLYGON);
        gl.glVertex2f(0.0f, 2.5f);
        gl.glVertex2f(0.0f, -2.5f);
        gl.glVertex2f(10.0f, -2.5f);
        gl.glVertex2f(10.0f, 2.5f);
        gl.glEnd();
        gl.glTranslatef(-1.0f,0.75f,0.0f);
        glu.gluDisk(quadric, 0, 0.5, 12, 1);
        gl.glTranslatef(0.0f,-1.5f,0.0f);
        glu.gluPartialDisk(quadric, 0, 0.75, 24, 1, 90, -180);
        gl.glTranslatef(11.0f,0.75f,0.0f);
        glu.gluPartialDisk(quadric, 0, 2.5, 24, 1, 0, 180);
        gl.glTranslatef(-3.75f,0.0f,0.0f);
        gl.glEndList();

        return SHAPE_SELECT_CYAN_DISK64;
    }

    @Override
    public void beforeDisplay(GL2 gl, GLU glu) {
    }

    @Override
    public void afterDisplay(GL2 gl, GLU glu) {
    }

    protected float cameraDistance(NodeModel object) {
        float[] cameraLocation = drawable.getCameraLocation();
        double distance = Math.sqrt(Math.pow((double) object.getNode().x() - cameraLocation[0], 2d)
                + Math.pow((double) object.getNode().y() - cameraLocation[1], 2d)
                + Math.pow((double) object.getNode().z() - cameraLocation[2], 2d));
        object.setCameraDistance((float) distance);

        return (float) distance;
    }

    public boolean isLod() {
        return true;
    }

    public boolean isSelectable() {
        return true;
    }

    public boolean isClickable() {
        return true;
    }

    public boolean isOnlyAutoSelect() {
        return false;
    }
}
