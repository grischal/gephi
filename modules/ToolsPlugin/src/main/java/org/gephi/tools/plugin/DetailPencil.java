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
package org.gephi.tools.plugin;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.gephi.tools.spi.MouseClickEventListener;
import org.gephi.tools.spi.Tool;
import org.gephi.tools.spi.ToolEventListener;
import org.gephi.tools.spi.ToolSelectionType;
import org.gephi.tools.spi.ToolUI;
import org.gephi.ui.tools.plugin.DetailPencilPanel;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.gephi.datalab.api.GraphElementsController;
import org.gephi.desktop.importer.api.ImportControllerUI;
import org.gephi.desktop.mrufiles.api.MostRecentFiles;
import org.gephi.desktop.project.api.ProjectControllerUI;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.project.api.ProjectInformation;
import org.gephi.tools.spi.NodeClickEventListener;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mathieu Bastian
 */
@ServiceProvider(service = Tool.class)
public class DetailPencil implements Tool {

    //Architecture
    private ToolEventListener[] listeners;
    private DetailPencilPanel detailPencilPanel;

    public DetailPencil() {
    }

    @Override
    public void select() {
    }

    @Override
    public void unselect() {
        listeners = null;
        detailPencilPanel = null;
    }

    @Override
    public ToolEventListener[] getListeners() {      
        listeners = new ToolEventListener[1];
        listeners[0] = new NodeClickEventListener() {
            @Override
            public void clickNodes(Node[] nodes) {
                Node n = nodes[0];

                if (n != null && n.getLabel() != null && !n.getLabel().equals("")) {
                    MostRecentFiles mru = Lookup.getDefault().lookup(MostRecentFiles.class);
                    ProjectInformation pi = Lookup.getDefault().lookup(ProjectControllerUI.class).getCurrentProject().getLookup().lookup(ProjectInformation.class);

                    System.out.println("PROJECT: " + pi.getFile().getAbsolutePath());   
                    String dir = "/";
                    for (String filePath : mru.getMRUFileList()) {
                        Path p = Paths.get(filePath);

                        dir = p.toString().substring(0, p.toString().length() - p.getFileName().toString().length());
                        System.out.println("PATH: " + p);
                        if (dir != null) {
                            break;
                        }
                    }
                    String path = dir + "subgraphs/sub_" + n.getLabel() + ".gexf";
                    System.out.println("Trying to load: " + path);
                    File file = new File(path);
                    if (file.exists()) {
                        FileObject fileObject = FileUtil.toFileObject(file);

//                if (!file.exists()) {
//                    NotifyDescriptor.Message msg = new NotifyDescriptor.Message(NbBundle.getMessage(CommandLineProcessor.class, "CommandLineProcessor.fileNotFound", file.getName()), NotifyDescriptor.WARNING_MESSAGE);
//                    DialogDisplayer.getDefault().notify(msg);
//                    return;
//                }
//                if (fileObject.hasExt(GEPHI_EXTENSION)) {
//                    ProjectControllerUI pc = Lookup.getDefault().lookup(ProjectControllerUI.class);
//                    try {
//                        pc.openProject(file);
//                    } catch (Exception ew) {
//                        ew.printStackTrace();
//                        NotifyDescriptor.Message msg = new NotifyDescriptor.Message(NbBundle.getMessage(CommandLineProcessor.class, "CommandLineProcessor.openGephiError"), NotifyDescriptor.WARNING_MESSAGE);
//                        DialogDisplayer.getDefault().notify(msg);
//                    }
//                    return;
//                } else {
                        ImportControllerUI importController = Lookup.getDefault().lookup(ImportControllerUI.class);
                        if (importController.getImportController().isFileSupported(FileUtil.toFile(fileObject))) {
                            importController.importFile(fileObject);
                        }
//                }
                    }
                }
            }
        };
        return listeners;
    }

    @Override
    public ToolUI getUI() {
        return new ToolUI() {
            @Override
            public JPanel getPropertiesBar(Tool tool) {
                detailPencilPanel = new DetailPencilPanel();
                return detailPencilPanel;
            }

            @Override
            public String getName() {
                return NbBundle.getMessage(DetailPencil.class, "DetailPencil.name");
            }

            @Override
            public Icon getIcon() {
                return new ImageIcon(getClass().getResource("/org/gephi/tools/plugin/resources/nodepencil.png"));
            }

            @Override
            public String getDescription() {
                return NbBundle.getMessage(DetailPencil.class, "DetailPencil.description");
            }

            @Override
            public int getPosition() {
                return 120;
            }
        };
    }

    @Override
    public ToolSelectionType getSelectionType() {
        return ToolSelectionType.SELECTION;
    }
}
