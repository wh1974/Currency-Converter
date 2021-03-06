package com.pyesmeadow.george.currencyconverter.currency.manager;

import com.pyesmeadow.george.currencyconverter.currency.Currency;
import com.pyesmeadow.george.currencyconverter.main.CurrencyConverter;
import com.pyesmeadow.george.currencyconverter.util.FontUtil;
import com.pyesmeadow.george.currencyconverter.util.FontUtil.FontVariation;

import javax.swing.*;
import java.awt.*;

public class CurrencyManagerDialog extends JDialog {

	private static final long serialVersionUID = 7762267343511187163L;

	private CurrencyManager currencyManager;
	// Scroll pane
	private JPanel panelCurrencies = new JPanel(new GridBagLayout());
	private JScrollPane scrollPaneCurrencies = new JScrollPane(panelCurrencies,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	// Navigation panel
	private JPanel panelNavigation = new JPanel(new GridBagLayout());
	private JButton btnAddCurrency = new JButton(new ImageIcon(CurrencyConverter.ADD_ICON));
	private JButton btnRefresh = new JButton("Refresh");
	private JButton btnReset = new JButton("Reset");
	private JButton btnClose = new JButton("Close");

	public CurrencyManagerDialog(CurrencyManager currencyManager)
	{
		this.currencyManager = currencyManager;

		this.setLayout(new GridBagLayout());

		// Setup components and panels

		// panelCurrencies

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1.0;
		c.insets = new Insets(4, 4, 4, 4);

		for (Currency currency : this.currencyManager.getCurrencyList().getCurrencies())
		{
			CurrencyManagerEntry panel = new CurrencyManagerEntry(currency);

			panelCurrencies.add(panel, c);
		}

		// panelNavigation

		c = new GridBagConstraints();
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(5, 5, 5, 5);

		panelNavigation.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelNavigation.add(btnAddCurrency, c);
		panelNavigation.add(btnRefresh, c);
		panelNavigation.add(btnReset, c);
		panelNavigation.add(btnClose, c);

		// Add components
		GridBagConstraints mainConstraints = new GridBagConstraints();
		mainConstraints.gridx = 0;
		mainConstraints.gridy = GridBagConstraints.RELATIVE;
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainConstraints.weightx = 1.0;
		mainConstraints.weighty = 1.0;

		add(scrollPaneCurrencies, mainConstraints);

		mainConstraints.weighty = 0.0;
		add(panelNavigation, mainConstraints);

		// Setup dialog
		this.registerComponentFontVariations();
		FontUtil.updateComponentFontVariations(CurrencyConverter.getFontProfile(), false);

		createListeners();

		setSize(new Dimension(380, 500));
		setMinimumSize(new Dimension(380, 400));
		setMaximumSize(new Dimension(600, 600));
		setTitle("Manage Currencies");
		setIconImages(CurrencyConverter.APP_ICONS);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	protected void createListeners()
	{
		this.btnAddCurrency.addActionListener(e ->
		{

			Currency c = DialogAddCurrency.showDialog();

			if (c != null) this.currencyManager.addCurrency(c);

		});

		this.btnRefresh.addActionListener(e ->
		{

			this.currencyManager.refreshFromJSON();

		});

		this.btnReset.addActionListener(e ->
		{

			this.currencyManager.resetCurrencies();

		});

		this.btnClose.addActionListener(e -> dispose());
	}

	public void updateDisplayedCurrencies()
	{
		panelCurrencies.removeAll();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1.0;
		c.insets = new Insets(5, 5, 5, 5);

		for (Currency currency : this.currencyManager.getCurrencyList().getCurrencies())
		{
			CurrencyManagerEntry panel = new CurrencyManagerEntry(currency);

			panelCurrencies.add(panel, c);
		}

		FontUtil.updateComponentFontVariations(CurrencyConverter.getFontProfile(), false);

		this.scrollPaneCurrencies.validate();
	}

	protected void registerComponentFontVariations()
	{
		FontUtil.registerComponentFontVariation(btnAddCurrency, FontVariation.SMALL_PLAIN);
		FontUtil.registerComponentFontVariation(btnRefresh, FontVariation.SMALL_PLAIN);
		FontUtil.registerComponentFontVariation(btnReset, FontVariation.SMALL_PLAIN);
		FontUtil.registerComponentFontVariation(btnClose, FontVariation.SMALL_PLAIN);
	}
}
