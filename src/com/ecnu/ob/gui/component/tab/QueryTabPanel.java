package com.ecnu.ob.gui.component.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.ecnu.ob.model.ActionType;
import com.ecnu.ob.model.Column;
import com.ecnu.ob.model.ColumnValue;
import com.ecnu.ob.model.ColumnValue.ColumnValueType;
import com.ecnu.ob.model.PlanTreeNode;
import com.ecnu.ob.model.QueryResult;
import com.ecnu.ob.utils.Pair;
import com.ecnu.ob.utils.PlanUtil;
import com.ecnu.ob.utils.Tree;

@SuppressWarnings("serial")
public class QueryTabPanel extends BaseTabPanel {
	
	public enum QueryTabType {
		NONE,
		EMPTY,
		TABLE,
		TEXT,
		PLAN,
	}
	
	private JTextArea cmdArea;
	
	private JButton execBtn;
	
	private JButton explainBtn;
	
	private JLabel resultLabel;
	
	private JComboBox dbNameBox;
	
	private JComboBox timeoutBox;
	
	private JPanel panelBottom;
	
	private JLabel detailLabel;
	
	public QueryTabPanel(String title, Vector<Column> headers, Vector<Vector<ColumnValue>> rows, boolean isEmptyRow,
			String defaultSql, String dbName, List<String> dbNameList, QueryTabType type) {
		super(title);
		this.type = TabType.TAB_QEURY;
		
		/*********************** top *********************/
		JPanel panelTop = new JPanel();
		
		cmdArea = new JTextArea();
		JScrollPane textScroll = new JScrollPane(cmdArea);
		cmdArea.setRows(6);
		cmdArea.setSize(800, 100);
		cmdArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		cmdArea.setLineWrap(true);
		this.cmdArea.setText(defaultSql);
		
		resultLabel = new JLabel("");
		
		JPanel btnPanel = new JPanel();
		JLabel dbLabel = new JLabel("Database:");
		dbNameBox = new JComboBox();
		dbNameBox.addItem("NONE");
		if(dbNameList != null) {
			for(String db: dbNameList) {
				dbNameBox.addItem(db);
			}
			if(dbName != null) {
				dbNameBox.setSelectedItem(dbName);
			}
		}
		
		dbNameBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					setTabTitle("[Query] " + getDbName());
				}
			}
		});
		
		timeoutBox = new JComboBox();
		timeoutBox.addItem("3s");
		timeoutBox.addItem("No Limit");
		timeoutBox.setSelectedIndex(0);
		
		
		execBtn = new JButton("Execute");
		execBtn.setPreferredSize(new Dimension(150, 28));
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdArea.setText("");
				setBottomPanel(new QueryResult(), QueryTabType.EMPTY);
				resultLabel.setText("");
				setTabTitle("[Query] " + getDbName());
			}
		});
		explainBtn = new JButton("Explain");
		
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(dbLabel);
		btnPanel.add(dbNameBox);
		btnPanel.add(new JLabel(" Timeout:"));
		btnPanel.add(timeoutBox);
		btnPanel.add(clearBtn);
		btnPanel.add(explainBtn);
		btnPanel.add(execBtn);
		
		panelTop.setLayout(new BorderLayout());
		panelTop.add(textScroll, BorderLayout.CENTER);
		
		JPanel commandPanel = new JPanel();
		
		commandPanel.setLayout(new BorderLayout());
		commandPanel.add(resultLabel, BorderLayout.WEST);
		commandPanel.add(btnPanel, BorderLayout.EAST);
		
		panelTop.add(commandPanel, BorderLayout.SOUTH);
		
		/********************* bottom ********************/
		panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout());
		if(type == QueryTabType.EMPTY) {
			setBottomPanel(new QueryResult(), type);
		} else {
			setBottomPanel(new QueryResult(headers, rows, isEmptyRow), type);
		}
		//split top and bottom
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setTopComponent(panelTop);
		splitPane.setBottomComponent(panelBottom);
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(155);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
	}
	
	public void setDbNameBox(List<String> dbNameList) {
		Object dbName = dbNameBox.getSelectedItem();
		dbNameBox.removeAllItems();
		dbNameBox.addItem("NONE");
		if(dbNameList != null) {
			for(String db: dbNameList) {
				dbNameBox.addItem(db);
			}
			if(dbName != null) {
				dbNameBox.setSelectedItem(dbName);
			}
		}
	}
	
	public void setBottomPanel(QueryResult result, QueryTabType type) {
		if(panelBottom != null) {
			panelBottom.removeAll();
			panelBottom.repaint();
			if(type == QueryTabType.TABLE) {
				JTable table;
				if(!result.isEmptyRow) {
					for(int i = 0; i < result.rows.size(); i++) {
						result.rows.get(i).add(0, new ColumnValue(i + 1, ColumnValueType.INT));
					}
				}
				if(result.headers != null && result.headers.size() > 0) {
					result.headers.add(0, new Column());
					table = new JTable(result.rows,result.headers) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					//µã»÷±íÍ·ÅÅÐò
					DefaultTableModel tableModel = new DefaultTableModel(result.rows,result.headers) {
						public Class<?> getColumnClass(int columnIndex) {
							Object obj = getValueAt(0, columnIndex);
							if(obj != null) {
								return obj.getClass();
							}
							return String.class;
						}
					};
					table.setModel(tableModel);
					table.setRowSorter(new TableRowSorter<TableModel>(tableModel));
				} else if(result.headerNames != null && result.headerNames.size() > 0) {
					result.headerNames.add(0, " ");
					table = new JTable(result.rows, result.headerNames) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
				} else {
					table = new JTable();
				}
				
				setColumnWidth(table);
				JScrollPane scrollPane = new JScrollPane(table);
				panelBottom.add(scrollPane, BorderLayout.CENTER);
			} else if(type == QueryTabType.TEXT) {
				JLabel label = new JLabel(result.text);
				label.setVerticalAlignment(JLabel.TOP);
				panelBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				panelBottom.add(label);
			} else if(type == QueryTabType.EMPTY) {
				JLabel label = new JLabel("Please enter SQL to execute.");
				label.setVerticalAlignment(JLabel.TOP);
				panelBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				panelBottom.add(label);
			} else if(type == QueryTabType.PLAN) {
				String planStr = result.text;
				if(planStr != null) {
					JPanel treePanel = new JPanel();
					treePanel.setLayout(new BorderLayout());
					JScrollPane topStrollPane = new JScrollPane(treePanel);
					
					Pair<Tree, String> ret = PlanUtil.reslove_explain_result(planStr);
					DefaultMutableTreeNode root = PlanUtil.createNode(ret.first, ret.second, 0);
					JTree planTree = new JTree(root);
					treePanel.add(planTree, BorderLayout.CENTER);
					PlanUtil.expandAll(planTree, new TreePath(root));
					planTree.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JTree tree = (JTree) e.getSource();
							int modes = e.getModifiers();
							TreePath path = tree.getPathForLocation(e.getX(), e.getY());
							if(path != null) {
								tree.setSelectionPath(path);
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
								if((modes & InputEvent.BUTTON1_MASK) != 0) {
									if(e.getClickCount() == 2) {
										//do nothing
									} else {
										detailLabel.setText("<html>" + ((PlanTreeNode)node.getUserObject()).detail + "</html>");
									}
								} 
							}
						}

						@Override
						public void mousePressed(MouseEvent e) {}

						@Override
						public void mouseReleased(MouseEvent e) {}

						@Override
						public void mouseEntered(MouseEvent e) {}

						@Override
						public void mouseExited(MouseEvent e) {}
						
					});
					
					JSplitPane splitPanel = new JSplitPane();
					JPanel infoPanel = new JPanel();
					infoPanel.setLayout(new BorderLayout());
					detailLabel = new JLabel("Logic plan detail infomation.");
					detailLabel.setVerticalAlignment(JLabel.TOP);
					
					infoPanel.add(detailLabel, BorderLayout.CENTER);
					JScrollPane bottomStrollPane = new JScrollPane(infoPanel);
					
					splitPanel.setTopComponent(topStrollPane);
					splitPanel.setBottomComponent(bottomStrollPane);
					splitPanel.setOneTouchExpandable(true);
					splitPanel.setDividerSize(1);
					splitPanel.setDividerLocation(500);
					splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
					
					panelBottom.add(splitPanel, BorderLayout.CENTER);
				}
				
			} else {
				//do nothing
			}
			
			panelBottom.validate();
		}
	}
	
	private void setColumnWidth(JTable table) {
		table.setRowHeight(25);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setIntercellSpacing(new Dimension(20, 0));
		JTableHeader header = table.getTableHeader();
		
		int rowCount = table.getRowCount();
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int)table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for(int row = 0; row < rowCount; row++) {
				int preferredWidth = (int)table.getCellRenderer(row, col)
						.getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferredWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
	
	
	public void setResultLabel(String result) {
		if(resultLabel != null) {
			resultLabel.setText(result);
		}
	}
	
	public String getSql() {
		String sql = null;
		if(cmdArea != null) {
			sql = cmdArea.getText().trim();
		}
		return sql;
	}

	public String getDbName() {
		String dbName = dbNameBox.getSelectedItem().toString();
		if(dbName.equals("NONE")) {
			dbName = null;
		}
		return dbName;
	}
	
	public int getTimeout() {
		int timeout = -1;
		String timeoutStr = timeoutBox.getSelectedItem().toString();
		if(!timeoutStr.equals("No Limit")) {
			timeout = Integer.parseInt(timeoutStr.replace("s", ""));
		}
		return timeout;
	}

	@Override
	public void setAction(AbstractAction action, ActionType actionType) {
		switch(actionType) {
		case EXECUTE_SQL_ACTION:
			if(execBtn != null) {
				execBtn.setAction(action);
			}
			break;
		case EXPLAIN_SQL_ACTION:
			if(explainBtn != null) {
				explainBtn.setAction(action);
			}
			break;
		}
	}
}
