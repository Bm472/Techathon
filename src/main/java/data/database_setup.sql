DROP TABLE IF EXISTS Login;
DROP TABLE IF EXISTS Progress;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Customer;

-- Create the Customer table
CREATE TABLE Customer
(
    customerID        INT IDENTITY(1,1) PRIMARY KEY,
    firstName         VARCHAR(50),
    lastName          VARCHAR(50),
    dob               DATE
);

-- Create the Login table
CREATE TABLE Login
(
    loginID  INT IDENTITY(1,1) PRIMARY KEY,
    customerID INT                NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(64)        NOT NULL,
    FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE
);

-- Create the Course table
CREATE TABLE Course
(
	courseID  INT IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL,
	description TEXT,
	content TEXT NOT NULL
);

-- Create the Progress table
CREATE TABLE Progress
(
	progressID  INT IDENTITY(1,1) PRIMARY KEY,
	customerID INT NOT NULL,
	courseID INT NOT NULL,
	value TINYINT DEFAULT 0,
	UNIQUE (customerID,courseID) ,
	FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE,
	FOREIGN KEY (courseID) REFERENCES Course (courseID) ON DELETE CASCADE
);
INSERT INTO Course (name,description,content) VALUES ('Mortgages','Learn about mortgages', 'Understanding Mortgages: A Comprehensive Guide

Purchasing a home is one of the most significant financial decisions youll make, and understanding mortgages is a crucial step in the process. Here’s a breakdown of what you need to know:

/~

What Is a Mortgage?

A mortgage is a loan specifically designed for purchasing property. It allows individuals to buy a home without paying the full price upfront. Instead, the borrower agrees to repay the loan over time, typically in monthly installments that include principal and interest.

/~

Types of Mortgages

There are various types of mortgages available, each suited to different needs:

Fixed-Rate Mortgages: These loans have a consistent interest rate throughout the term, providing stability in monthly payments.

Adjustable-Rate Mortgages (ARMs): These start with a lower fixed rate for an initial period and then adjust periodically based on market rates.

Government-Backed Loans: These include FHA loans, VA loans, and USDA loans, often catering to first-time homebuyers or specific demographics.

Interest-Only Mortgages: Borrowers pay only the interest for a set period before starting to pay down the principal.

/~

Key Terms to Know

Principal: The amount of money borrowed.

Interest: The cost of borrowing the money, expressed as a percentage.

Amortization: The process of gradually paying off the loan through regular payments.

Loan-to-Value Ratio (LTV): The ratio of the loan amount to the propertys value, often expressed as a percentage.

Private Mortgage Insurance (PMI): Insurance that protects the lender if the borrower defaults on the loan, typically required for down payments less than 20%.

/~

How to Qualify for a Mortgage

Lenders assess several factors to determine eligibility:

Credit Score: A higher credit score can help secure better interest rates.

Debt-to-Income Ratio (DTI): This ratio compares your monthly debt payments to your income. A lower DTI is preferred.

Down Payment: While 20% is ideal, some loans allow smaller down payments.

Employment History: Stable and consistent income is crucial for approval.

/~

Steps to Get a Mortgage

Determine Your Budget: Assess your financial situation to understand how much you can afford.

Check Your Credit Report: Ensure your credit score is in good standing.

Get Pre-Approved: This step shows sellers that youre a serious buyer.

Shop for Lenders: Compare interest rates, fees, and terms from different lenders.

Submit Your Application: Provide necessary documents, such as proof of income and tax returns.

Close the Loan: Review and sign the final documents before receiving the keys to your new home.

/~

Common Pitfalls to Avoid

Overborrowing: Borrowing more than you can comfortably repay can lead to financial strain.

Ignoring Additional Costs: Remember to budget for property taxes, insurance, and maintenance.

Skipping Pre-Approval: Not getting pre-approved can hinder your ability to make competitive offers.

Focusing Solely on Interest Rates: Consider the overall loan terms, not just the interest rate.

/~

Conclusion

Understanding mortgages is essential for making informed decisions about homeownership. By familiarizing yourself with the process, terms, and types of loans, you can navigate the journey with confidence and find the right mortgage for your needs.');
INSERT INTO Course (name,description,content) VALUES ('Pensions','Learn about pensions', 'Understanding Pensions A Comprehensive Guide

Planning for retirement is an important financial goal, and pensions play a key role in ensuring financial security during your later years. Here is an overview of what you need to know about pensions

/~

What Is a Pension

A pension is a retirement savings plan that provides regular income after you retire. It is typically funded through contributions from your employer, yourself, or both, and the money grows over time until you are ready to retire.

/~

Types of Pensions

There are several types of pensions available to suit different needs and employment scenarios

Defined Benefit Pensions These guarantee a specific amount of income in retirement, based on factors such as salary and years of service

Defined Contribution Pensions Contributions are invested, and the retirement income depends on the performance of those investments

State Pension Provided by the government, this offers a basic level of income to retirees who have made sufficient National Insurance contributions

Personal Pension Plans Private savings plans that individuals can set up independently of their employer

/~

Key Terms to Know

Contribution The money paid into your pension by you and or your employer

Annuity A financial product that provides a guaranteed income in retirement, often purchased with your pension savings

Vesting Period The minimum time you must work for an employer to qualify for their pension plan

Pension Pot The total amount of money saved in your pension plan

Auto-Enrolment A government initiative requiring employers to automatically enroll eligible workers into a pension scheme

/~

How to Start a Pension Plan

Determine Your Retirement Goals Consider how much money you will need to live comfortably in retirement

Research Your Options Understand the different pension plans available to you, including workplace and private pensions

Contribute Regularly Make consistent contributions to your pension to build your savings over time

Review Your Plan Periodically Assess your pension performance and adjust your contributions as needed

/~

Common Pitfalls to Avoid

Delaying Contributions The earlier you start saving, the more time your money has to grow

Underestimating Costs Consider the impact of inflation and rising living expenses in retirement

Not Reviewing Investments Ensure your pension investments align with your risk tolerance and goals

Withdrawing Funds Early Avoid dipping into your pension savings before retirement unless absolutely necessary

/~

Conclusion

Pensions are a vital part of planning for a secure and comfortable retirement. By understanding the different types of pensions, key terms, and best practices, you can make informed decisions and take control of your financial future.');
INSERT INTO Course (name,description,content) VALUES ('Savings','Learn about   savings', 'Understanding Savings A Comprehensive Guide

Saving money is an essential financial habit that helps secure your future and achieve both short-term and long-term goals. Here is a detailed overview of the basics of savings

/~

What Are Savings

Savings refer to the portion of your income that you set aside for future use rather than spending immediately. Savings can be used for emergencies, large purchases, or to achieve specific financial goals.

/~

Types of Savings

There are various ways to save money, depending on your goals and needs

Emergency Savings A fund set aside to cover unexpected expenses, such as medical bills or car repairs

Short-Term Savings Money saved for immediate goals, like a vacation or a new gadget

Long-Term Savings Funds dedicated to major life goals, such as buying a home or retirement

High-Interest Savings Accounts Accounts that offer higher interest rates to grow your savings more quickly

/~

Key Terms to Know

Interest The money earned on your savings, often expressed as a percentage

Compound Interest Interest calculated on the initial principal and also on the accumulated interest from previous periods

Savings Rate The percentage of your income that you save regularly

Liquidity The ease with which you can access your savings without penalties

Budget A plan for managing your income and expenses to allocate funds for savings

/~

How to Build Your Savings

Set Clear Goals Identify what you are saving for and how much you need

Create a Budget Track your income and expenses to determine how much you can save each month

Automate Savings Set up automatic transfers to a savings account to ensure consistent contributions

Choose the Right Account Research savings accounts and select one that aligns with your goals and offers favorable terms

Review and Adjust Periodically evaluate your savings plan and make changes as needed

/~

Common Pitfalls to Avoid

Not Having a Plan Saving without specific goals can reduce motivation and focus

Dipping Into Savings Avoid using savings for non-essential expenses

Ignoring Interest Rates Not all savings accounts offer the same returns, so compare options carefully

Delaying Savings Starting late reduces the time for your savings to grow through compound interest

/~

Conclusion

Savings are a cornerstone of financial stability and success. By understanding the types of savings, key terms, and best practices, you can build a strong foundation for achieving your financial goals and safeguarding your future.');
INSERT INTO Course (name,description,content) VALUES ('Investments','Learn about investments', 'Understanding Investments A Comprehensive Guide

Investing is a powerful way to grow your wealth and achieve financial independence. Here is a detailed overview of investments to help you make informed decisions

/~

What Are Investments

Investments refer to the act of allocating money into assets with the goal of generating returns or increasing value over time. Investments can help you build wealth, beat inflation, and achieve long-term financial goals.

/~

Types of Investments

There are various types of investments to suit different goals and risk levels

Stocks Shares of ownership in a company that can offer returns through price appreciation and dividends

Bonds Fixed-income securities that represent a loan made by an investor to a borrower, typically a corporation or government

Mutual Funds Pooled investments managed by professionals that invest in a diversified portfolio of assets

Real Estate Property investments that generate income through rent or increase in value over time

Exchange-Traded Funds ETFs Investments that track an index or sector and are traded on stock exchanges like stocks

Commodities Physical goods like gold, oil, or agricultural products that can be traded

Cryptocurrencies Digital currencies that use blockchain technology for secure transactions

/~

Key Terms to Know

Diversification Spreading investments across different asset classes to reduce risk

Risk Tolerance The level of risk you are willing to take with your investments

Return The profit earned on an investment, often expressed as a percentage

Asset Allocation The strategy of dividing investments among different categories like stocks, bonds, and cash

Volatility The degree of variation in the price of an investment over time

/~

How to Start Investing

Set Clear Goals Determine what you are investing for, such as retirement, a home, or wealth creation

Understand Your Risk Tolerance Assess how much risk you are comfortable taking based on your financial situation and goals

Educate Yourself Learn about the different investment options and strategies

Start Small Begin with a modest amount to gain experience and build confidence

Diversify Your Portfolio Spread your investments to balance risk and reward

Monitor Your Investments Regularly review and adjust your portfolio to stay aligned with your goals

/~

Common Pitfalls to Avoid

Lack of Research Investing without understanding the asset or market can lead to losses

Emotional Decisions Avoid making impulsive decisions based on fear or greed

Overlooking Fees High fees can erode your returns over time, so choose cost-effective options

Ignoring Diversification Putting all your money in one investment increases risk

Timing the Market Trying to predict market highs and lows can be risky and ineffective

/~

Conclusion

Investments are an essential tool for building wealth and securing your financial future. By understanding the types of investments, key terms, and strategies, you can make informed choices and create a diversified portfolio that aligns with your goals and risk tolerance.');