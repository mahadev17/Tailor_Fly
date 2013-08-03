/**
 * 
 */
package com.linkites.Constants;

import psl.api.ssapi.client.SSRestServiceClient;

/**
 * @author apple
 *
 */
public class Constant {

	
	public final static int USER_SIGN_IN = 1;
	public final static int USER_SIGN_UP = 2;
	
	// Pages Constants 
	public final static int HOME_PAGE = 1;
	public final static int HOTELS_PAGE = 2;
	public final static int HOLIDAYS_PAGE = 3;
	public final static int FLIGHTS_PAGE = 4;
	public final static int BUS_PAGE = 5;
	public final static int OFFERS_PAGE = 6;
	public final static int KASHMIR_TOURS_PAGE = 7;
	
	//Adaptor Constants 
	public final static int HOTEL_CELL = 1;
	public final static int ROOM_CELL = 2;
	
	//------------------- City list APi--------------------------------------------
	public final static String SEARCH_CITYS = "http://gd.geobytes.com/AutoCompleteCity?q=";
	
	//--------------------Red Bus APIS Configurations-----------------------------
	public final static String RED_BUS_CONSUMER_KEY = "jhVNRx2VbhSdVmKSlcq2ow2vMVICin"; 
	public final static String RED_BUS_CONSUMER_SECRET = "cwAP2uWLpVnceauQtxUkU6VM0CV2QY";
	
	public static final SSRestServiceClient client = SSRestServiceClient
	            .getInstance("http://api.seatseller.travel:9191", RED_BUS_CONSUMER_KEY,
	            		RED_BUS_CONSUMER_SECRET);
	 
	public final static String RED_BUS_CITIES = "http://api.seatseller.travel/sources";
	
	//INPUT CITY ID FROM CITIES API 
	public final static String RED_BUS_DESTINATIONS = "http://api.seatseller.travel/destinations?source=";
	
	//INPUT CITY ID SOURCE AND DESTIONATIONS WITH DATE OF JOURNEY 
	public final static String RED_BUS_BOOKING_SERVICE = "http://api.seatseller.travel/availabletrips?source=%s&destination=%s&doj=%s";
	
	//INPUT TRIP ID  
	public final static String RED_BUS_TRIP_DETAIL = "http://api.seatseller.travel/tripdetails?id=";

	//INPUT BOARDING ID  
	public final static String RED_BUS_BOARDING_POINT = "http://api.seatseller.travel/boardingPoint?id=";

//	Input:
//
//		 Block Request
//		     Parameter  	                            Data Type 	                                Description
//		                       availableTripID 	                              long 	Available Trip Id Selected (e.g. 100000005744040674)
//		                       inventoryItems [ ] 	 defined below as inventoryItems[] 	 Inventory Item details like fare,passenger deatils etc.
//
//
//		 invetoryItems [ ] 
//		                    Parameter 	                                Data Type 	             Description
//		                    seatname 	                                    string 	Valid seat name(e.g. A15)
//		                         fare 	                                   double 	Fare of the seat in Rs(e.g. 1000)
//		                     ladiesSeat 	                                    string 	Seat reserved for ladies(e.g. true or false)
//		                    passenger [ ] 	         	Passenger details like name,email etc.
//
//		passenger [ ] 
//		                       Parameter 	                                Data Type 	             Description
//		                        name 	                                   string 	Name
//		                        mobile 	                                   long 	Mobile no
//		                          title 	                                   string 	Like Mr. Ms.
//		                        email 	                                   string 	Email Id
//		                         age 	                                   long 	Age
//		                       gender 	                                   string 	Gender 
	public final static String RED_BUS_BLOCK_TICKET = "http://api.seatseller.travel/blockTicket";
	
	//INPUT BLOCK KEY TO BOOK TICKETS
	public final static String RED_BUS_CONFIRM_TICKET = "http://api.seatseller.travel/bookticket?blockKey=";
	 
	//INPUT TICKET IDENTIFICATOIN NUMBER TO CANCEL TICKETS
	public final static String RED_BUS_CANCEL_TICKET_DETAILS = "http://api.seatseller.travel/cancellationdata?tin=";
	
	//INPUT  tin 	                             string 	Ticket identification number (e.g. S436228AS3)
    //seatToCancel 	                             string 	Seat Name to be cancelled(e.g. 16)
	public final static String RED_BUS_CANCEL_TICKET = "http://api.seatseller.travel/cancelticket";
	
	//INPUT TICKET INFORMATION NUMBER 
	public final static String RED_BUS_TICKET_INFORMATION = "http://api.seatseller.travel/ticket?tin=";

	//INPUT BLOCK KEY 
	public final static String RED_BUS_CHECKED_BOOKED_TICKET = "http://api.seatseller.travel/checkBookedTicket?blockKey=";

	//--------------------EAN Hotel APIS Configurations----------------------------- 
	public final static String API_KEY = "kan3brmrb3j4qqbezr8kmzna";
	public final static String SHARD_SECRET = "3d8u985J"; 
	public final static String EAN_IMAGES_BASE_URL = "http://images.travelnow.com";
//	<HotelListRequest>
//    	<city>Seattle</city>
//    	<stateProvinceCode>WA</stateProvinceCode>
//    	<countryCode>US</countryCode>
//    	<arrivalDate>8/1/2013</arrivalDate>
//    	<departureDate>8/3/2013</departureDate>
//    	<RoomGroup>
//        <Room>
//            <numberOfAdults>2</numberOfAdults>
//        </Room>
//        </RoomGroup>
//        <numberOfResults>25</numberOfResults>
//    </HotelListRequest>
	//public final static String HOTEL_LISTING = "http://api.ean.com/ean‑services/rs/hotel/v3/list?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD&customerUserAgent=mobile&customerIpAddress=xxx&supplierCacheTolerance=MED&numberOfResults=250&supplierCacheTolerance=MED_ENHANCED";
	public final static String HOTEL_LISTING = "https://api.eancdn.com/ean-services/rs/hotel/v3/list?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";

		
//	<HotelInformationRequest>
//    	<hotelId>122212</hotelId>
//    	<options>0</options>
//    </HotelInformationRequest>
	public final static String HOTEL_INFO = "https://api.eancdn.com/ean-services/rs/hotel/v3/info?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelRoomAvailabilityRequest>
//    	<hotelId>106347</hotelId>
//    	<arrivalDate>8/1/2013</arrivalDate>
//    	<departureDate>8/3/2013</departureDate>
//    	<includeDetails>true</includeDetails>
//    	<RoomGroup>
//        	<Room>
//            <numberOfAdults>2</numberOfAdults>
//            </Room>
//            </RoomGroup>
//      </HotelRoomAvailabilityRequest>
	public final static String HOTEL_ROOM_AVIAILABLE = "https://api.eancdn.com/ean-services/rs/hotel/v3/avail?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelRateRulesRequest>
//    	<hotelId>338894</hotelId>
//    	<arrivalDate>8/1/2013</arrivalDate>
//    	<departureDate>8/3/2013</departureDate>
//    	<supplierType>S</supplierType>
//    	<rateCode>RA1</rateCode>
//    	<roomTypeCode>N2D</roomTypeCode>
//    	<RoomGroup>
//        <Room>
//            <numberOfAdults>2</numberOfAdults>
//        </Room>
//        </RoomGroup>
//    </HotelRateRulesRequest>
	public final static String HOTEL_RULES = "https://api.eancdn.com/ean-services/rs/hotel/v3/rules?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<AlternatePropertyListRequest>
//    	<originalHotelId>164116</originalHotelId>
//    	<originalAverageNightlyRate>100.00</originalAverageNightlyRate>
//    	<arrivalDate>8/1/2013</arrivalDate>
//    	<departureDate>8/3/2013</departureDate>
//    	<RoomGroup>
//        <Room>
//            <numberOfAdults>2</numberOfAdults>
//        </Room>
//        </RoomGroup>
//    </AlternatePropertyListRequest>
	public final static String HOTEL_ALTPROPS = "https://api.eancdn.com/ean-services/rs/hotel/v3/altProps?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelPaymentRequest>
//    	<hotelId>122212</hotelId>
//    	<supplierType>E</supplierType>
//    	<rateType>MerchantStandard</rateType>
//    </HotelPaymentRequest>
	public final static String HOTEL_PAYMENTTYPES = "https://api.eancdn.com/ean-services/rs/hotel/v3/paymentInfo?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelRoomReservationRequest>
//    	<hotelId>106347</hotelId>
//    	<arrivalDate>8/1/2013</arrivalDate>
//    	<departureDate>8/3/2013</departureDate>
//    	<supplierType>E</supplierType>
//    	<rateKey>af00b688-acf4-409e-8bdc-fcfc3d1cb80c</rateKey>
//    	<roomTypeCode>198058</roomTypeCode> 
//    	<rateCode>484072</rateCode>
//    	<chargeableRate>231.18</chargeableRate>
//    	<RoomGroup>
//        	<Room>
//            	<numberOfAdults>2</numberOfAdults>
//            	<firstName>test</firstName>
//            	<lastName>tester</lastName>
//            	<bedTypeId>23</bedTypeId>
//            	<smokingPreference>NS</smokingPreference>
//            	</Room>
//            	</RoomGroup>
//            	<ReservationInfo>
//            	<email>test@travelnow.com</email>
//            	<firstName>test</firstName>
//            	<lastName>tester</lastName>
//            	<homePhone>2145370159</homePhone>
//            	<workPhone>2145370159</workPhone>
//            	<creditCardType>CA</creditCardType>
//            	<creditCardNumber>5401999999999999</creditCardNumber>
//            	<creditCardIdentifier>123</creditCardIdentifier>
//            	<creditCardExpirationMonth>11</creditCardExpirationMonth>
//            	<creditCardExpirationYear>2015</creditCardExpirationYear>
//            	</ReservationInfo>
//            	<AddressInfo>
//            	<address1>travelnow</address1>
//            	<city>Seattle</city>
//            	<stateProvinceCode>WA</stateProvinceCode>
//            	<countryCode>US</countryCode>
//            	<postalCode>98004</postalCode>
//            	</AddressInfo>
//            	</HotelRoomReservationRequest>
	public final static String HOTEL_RESERVATION = "https://api.eancdn.com/ean-services/rs/hotel/v3/res?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelItineraryRequest>
//    	<itineraryId>1234</itineraryId>
//    	<email>test@travelnow.com</email>
//    </HotelItineraryRequest>
	public final static String HOTEL_VIEW_ITINERARY = "https://api.eancdn.com/ean-services/rs/hotel/v3/itin?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<HotelRoomCancellationRequest>
//    	<itineraryId>1234</itineraryId>
//    	<email>test@travelnow.com</email>
//    	<reason>COP</reason>
//    	<confirmationNumber>1234</confirmationNumber>
//    </HotelRoomCancellationRequest>
	public final static String HOTEL_CANCEL_ITINERARY = "https://api.eancdn.com/ean-services/rs/hotel/v3/cancel?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
//	<LocationInfoRequest>
//    	<locale>en_US</locale>
//    	<destinationString>Seattle, WA</destinationString>
//    </LocationInfoRequest>
	public final static String HOTEL_GEO_SEARCH = "https://api.eancdn.com/ean-services/rs/hotel/v3/geoSearch?cid=55505&minorRev=99&apiKey=kan3brmrb3j4qqbezr8kmzna&locale=en_US&currencyCode=USD";
	
}
