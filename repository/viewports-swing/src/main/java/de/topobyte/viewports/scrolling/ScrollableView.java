package de.topobyte.viewports.scrolling;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;

public class ScrollableView<T extends JComponent & ViewportWithSignals & HasScene & HasMargin> extends JPanel {
   private static final long serialVersionUID = 1729551468089935167L;

   public ScrollableView(T view) {
      super(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      GridBagConstraintsEditor editor = new GridBagConstraintsEditor(c);
      InputMap inputMap = this.getInputMap(2);
      inputMap.put(KeyStroke.getKeyStroke(521, 128), "Ctrl++");
      inputMap.put(KeyStroke.getKeyStroke(45, 128), "Ctrl+-");
      inputMap.put(KeyStroke.getKeyStroke(49, 128), "Ctrl+1");
      ActionMap actionMap = this.getActionMap();
      actionMap.put("Ctrl++", new ZoomAction(view, ZoomAction.Type.IN));
      actionMap.put("Ctrl+-", new ZoomAction(view, ZoomAction.Type.OUT));
      actionMap.put("Ctrl+1", new ZoomAction(view, ZoomAction.Type.IDENTITY));
      JScrollBar scrollerH = new JScrollBar(0);
      JScrollBar scrollerV = new JScrollBar(1);
      SceneBoundedRangeModel<T> rangeH = new SceneBoundedRangeModel<>(view, true);
      scrollerH.setModel(rangeH);
      SceneBoundedRangeModel<T> rangeV = new SceneBoundedRangeModel<>(view, false);
      scrollerV.setModel(rangeV);
      editor.fill(1);
      editor.gridPos(0, 0).weight(1.0, 1.0);
      this.add(view, c);
      editor.gridPos(0, 1).weight(1.0, 0.0);
      this.add(scrollerH, c);
      editor.gridPos(1, 0).weight(0.0, 1.0);
      this.add(scrollerV, c);
      PanMouseAdapter<T> panAdapter = new PanMouseAdapter(view);
      this.addMouseListener(panAdapter);
      this.addMouseMotionListener(panAdapter);
   }
}
