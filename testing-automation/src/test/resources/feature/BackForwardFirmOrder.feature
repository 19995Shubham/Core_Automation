Feature: Back And Forward Order

  # Scenario 1 explains
  # Update dealer email address
  # Place Back order for one vehicle model
  @core
  Scenario:Scenario 1: Dealer Backward Order
    Given Set up dealer email address
    Then Open MA Vehicle Url and login with Ringwood dealer
       And Navigate to Vehicle Order Page
      And Enter model “200SAH5G15P”, colour “25D” and trim “D4H”
    Then Click on Search
      And Validate “No vehicle was found” message
      And Click on Submit Back Order
    Then Navigate to Stock Locator Page
      And Search for required Model
      And Click on "Back Order" folder
    Then Validate the generated request number

  # Scenario 2 explains
  # Import text file with all forward orders from different dealers
  # Validate vehicle count for each group w.r.t. each dealer
  @core
  Scenario:Scenario 2: Import File for Dealer Forward Orders
    Given Open MA MCOrder Url and login with user SBZHOU
    Then Navigate to Load Forward Orders page
    And Enter Forward id as "A23-11-11" and click on "Load Allocations"
    And Click on "Check Forward Order" button
    And Enter Open Date and Cut off date of 3 day
    Then Import the Text file
    And Validate "SOP%" message if any
    And Validate vehicle count for each group
    And Expand First Group
    And Validate vehicle count for each dealer
    Then click on Save

   # Scenario 3 explains
   # Fully met order for dealer Ringwood
   # Validate forward order count in stock locator
  @core
  Scenario:Scenario 3: Fully Met Dealer Vehicle Orders
    Given Open MA Vehicle Url and login with Ringwood dealer
    Then Navigate to Forward Orders Entry page
    And Validate Order and Back Order components
    And Validate Forward order Id with Cut Off Date
    And Validate all groups with count displayed for that dealer
    Then Click on SOP Summary
    And Validate "Forward Order SOP Summary" pop-up
    And Validate minimum order quantity in SOP summary pop-up
    And Close SOP Summary Pop-up
    Then Navigate to Stock Locator Page from Forward Order Entry Page
    And Click on "Forward Order" folder
    And Validate total forward count
    And Logout from the application


  # Scenario 4 explains
  # Partially met order for dealer Essendon
  # Validate forward order count in stock locator
  @core
  Scenario:Scenario 4: Partially Met Dealer Vehicle Orders
    Given Open MA Vehicle Url and login with Essendon dealer
    Then Navigate to Forward Orders Entry page
      And Validate Order and Back Order components
      And Validate Forward order Id with Cut Off Date
      And Search For same Model in Place Order
    Then Select forward Id from Dropdown
    Then Select multi color by using Keyboard
      And Click on Fill Selected BackOrders
    Then Click on SOP Summary
      And Validate "Forward Order SOP Summary" pop-up
      And Close SOP Summary Pop-up
    Then Navigate to Stock Locator Page from Forward Order Entry Page
      And Click on "Forward Order" folder
      And Validate total forward count

  # Scenario 5 explains
  # Finalize forward order SOP% and validate Met/Not Met Status
    @core
  Scenario:Scenario 5: MC Order(Finalize forward order SOP%)
    Given Open MA MCOrder Url and login with user SBZHOU
      Then Navigate to Forward Order Dates
      And Enter forward order ID and click on Search on forward order dates Page
      And Double Click on retrieved forwardId
      Then Update cutoff date to -2 days back date
      Then Navigate to Finalize forward order SOP%
    And Enter forward order ID and click on Search
    And Validate all the placed order with status as MET & Not MET
    Then click on Finalize
    And Validate Only MET forward orders still be there
    Then Open MA Vehicle Url and login with Ringwood dealer
    And Navigate to Forward Orders Entry page
    And Validate changes in Dealer Login





