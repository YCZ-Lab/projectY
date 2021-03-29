package com.ysh.projectY;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.ysh.projectY.entity.SchoolsInfo;
import com.ysh.projectY.service.SchoolsInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class MapsTest {

    @Autowired
    SchoolsInfoService schoolsInfoService;

    //        @Test
    public void directions() {
        final GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.queryRateLimit(500);
        builder.apiKey("AIzaSyC3LSbHlnSlG5aWQE3hBJYFt8E9KgxowJ8");
        final GeoApiContext context = builder.build();

        DirectionsApiRequest apiRequest = DirectionsApi.newRequest(context);
        apiRequest.origin(new com.google.maps.model.LatLng(49.33497705321198, -123.13709540324582));
        apiRequest.destination(new com.google.maps.model.LatLng(49.34546990428592, -123.15044803208147));
        apiRequest.mode(TravelMode.WALKING); //set travelling mode
        apiRequest.setCallback(new com.google.maps.PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                System.out.println("onResult");
                DirectionsRoute[] routes = result.routes;
                System.out.println(routes.length);
                for (DirectionsRoute route : routes) {
                    System.out.println(route.copyrights);
                    System.out.println(route);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println("Error:" + e);
            }
        });

        System.out.println("***");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void geocoding() throws InterruptedException, ApiException, IOException {
        final GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.queryRateLimit(500);
        builder.apiKey("AIzaSyC3LSbHlnSlG5aWQE3hBJYFt8E9KgxowJ8");
        final GeoApiContext context = builder.build();
        final List<SchoolsInfo> schoolsInfoList = schoolsInfoService.findAll();
        for (int i = 0; i < schoolsInfoList.size(); i++) {
            SchoolsInfo schoolsInfo = schoolsInfoList.get(i);
            if (schoolsInfo.getId() != 926) {
                continue;
            }
            System.out.println(schoolsInfo.getId());
            String schoolName = schoolsInfo.getSchoolName();
            schoolName = "Alif Academy Education";
            final GeocodingApiRequest apiRequest = GeocodingApi.newRequest(context).region("CA").bounds(new LatLng(48.224556, -139.0570702), new LatLng(60.0001489, -114.054221));
            final GeocodingResult[] results = apiRequest.address(schoolName).await();
            if (results.length > 1) {
                System.out.println("不是唯一结果");
            } else {
//                System.out.print("唯一结果正确\t");
            }
            for (GeocodingResult result : results) {
                System.out.println(">>>>");
//            49.345462914485964, -123.15034074372258
                final Geometry geometry = result.geometry;
                System.out.println("\t" + geometry.location);
//                System.out.println("\t" + geometry.locationType);
                String formatAddress = result.formattedAddress;
                System.out.println("\t" + formatAddress);
//                boolean isTypeCorrect = false;
//                for (AddressType type : result.types) {
//                    if (type == AddressType.PRIMARY_SCHOOL || type == AddressType.SECONDARY_SCHOOL || type == AddressType.SCHOOL) {
//                        isTypeCorrect = true;
//                        break;
//                    }
//                }
//                if (!isTypeCorrect) {
//                    System.out.print("类型不同\t");
//                } else {
////                    System.out.print("类型相同\t");
//                }
                boolean isPostcodeCorrent = false;
                final AddressComponent[] addressComponents = result.addressComponents;
                String temp = "";
                outer:
                for (AddressComponent addressComponent : addressComponents) {
                    for (AddressComponentType type : addressComponent.types) {
                        if (type == AddressComponentType.POSTAL_CODE) {
                            if (addressComponent.longName.equals(schoolsInfo.getPostalCode())) {
                                isPostcodeCorrent = true;
                                temp = addressComponent.longName;
                                break outer;
                            } else {
                                temp = addressComponent.longName;
                            }
                        }
                    }
                }
//                if (!isPostcodeCorrent) {
////                    System.out.println(schoolsInfo.getPostalCode());
////                    System.out.println(temp);
//                    System.out.print("邮编不同\t");
//                } else {
////                    System.out.print("邮编相同\t");
//                }
                System.out.println("\t" + temp);
                System.out.println("\t" + result.placeId);
            }
            System.out.println("");
        }
    }

    //    @Test
    public void test() throws InterruptedException, ApiException, IOException {
        final GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.queryRateLimit(500);
        builder.apiKey("AIzaSyC3LSbHlnSlG5aWQE3hBJYFt8E9KgxowJ8");
        final GeoApiContext context = builder.build();
        final List<SchoolsInfo> schoolsInfoList = schoolsInfoService.findAllByPostalcode();
        for (int i = 0; i < schoolsInfoList.size(); i++) {
            SchoolsInfo schoolsInfo = schoolsInfoList.get(i);
//            if (schoolsInfo.getId() != 53) {
//                continue;
//            }
            System.out.print("ID: " + schoolsInfo.getId() + "\t");
            String schoolName = schoolsInfo.getSchoolName();
            System.out.print(schoolName + "\t");
            final GeocodingApiRequest apiRequest = GeocodingApi.newRequest(context).region("CA");//.bounds(new LatLng(48.224556, -139.0570702), new LatLng(60.0001489, -114.054221));
            final GeocodingResult[] results = apiRequest.address(schoolName).await();
            if (results.length > 1) {
                System.out.print("\t不是唯一结果");
                System.out.print("\t" + results.length);
                System.out.println("");
                continue;
            }
            for (GeocodingResult result : results) {
//            49.345462914485964, -123.15034074372258
                final Geometry geometry = result.geometry;
                System.out.print(geometry.location.lat + "\t");
                schoolsInfo.setLatitude(geometry.location.lat);
                System.out.print(geometry.location.lng + "\t");
                schoolsInfo.setLongitude(geometry.location.lng);
//                System.out.println(geometry.locationType);
//                schoolsInfo.setLatitude();
                String formatAddress = result.formattedAddress;
                System.out.print(formatAddress + "\t");
                schoolsInfo.setSchoolAddressGoogleMap(formatAddress);
//                schoolsInfo.setSchoolAddressGoogleMap(formatAddress);
//                boolean isTypeCorrect = false;
//                for (AddressType type : result.types) {
//                    if (type == AddressType.PRIMARY_SCHOOL || type == AddressType.SECONDARY_SCHOOL || type == AddressType.SCHOOL) {
//                        isTypeCorrect = true;
//                        break;
//                    }
//                }
//                if (!isTypeCorrect) {
//                    System.out.println("类型不同");
//                    for (AddressType type : result.types) {
//                        System.out.print("\t" + type + "\t");
//                    }
//                    System.out.println("");
//                } else {
////                    System.out.print("类型相同\t");
//                }
                boolean isPostcodeCorrent = false;
                final AddressComponent[] addressComponents = result.addressComponents;
                String temp = "";
                outer:
                for (AddressComponent addressComponent : addressComponents) {
                    for (AddressComponentType type : addressComponent.types) {
                        if (type == AddressComponentType.POSTAL_CODE) {
                            if (addressComponent.longName.equals(schoolsInfo.getPostalCode())) {
                                isPostcodeCorrent = true;
                                break outer;
                            } else {
                                temp = addressComponent.longName;
                            }
                        }
                    }
                }
                System.out.print(temp + "\t");
                schoolsInfo.setPostalCodeGoogleMap(temp);
                //                if (!isPostcodeCorrent) {
////                    System.out.println(schoolsInfo.getPostalCode());
////                    System.out.println(temp);
//                    System.out.println("邮编不同");
//                    System.out.println("\t" + temp);
//                    System.out.println("\t" + schoolsInfo.getPostalCode());
//                } else {
////                    System.out.print("邮编相同\t");
//                }
                System.out.print(result.placeId + "\t");
                schoolsInfo.setPlaceIdGoogleMap(result.placeId);
            }
//            schoolsInfoService.saveAndFlush(schoolsInfo);
            System.out.println("");
        }
    }

    //    @Test
    public void place() throws InterruptedException, ApiException, IOException {
        final GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.queryRateLimit(500);
        builder.apiKey("AIzaSyC3LSbHlnSlG5aWQE3hBJYFt8E9KgxowJ8");
        final GeoApiContext context = builder.build();
        final List<SchoolsInfo> all = schoolsInfoService.findAll();
        for (SchoolsInfo schoolsInfo : all) {
            int id = schoolsInfo.getId();
            if (id != 2351) {
                continue;
            }
            String placeId = schoolsInfo.getPlaceIdGoogleMap();
            if (placeId.equals("unknown")) {
                continue;
            }
            final PlaceDetails details = PlacesApi.placeDetails(context, placeId).region("CA").await();
            String name = details.name;
            if (name != null && !"".equals(name)) {
                schoolsInfo.setSchoolNameGoogleMap(details.name);
            } else {
                schoolsInfo.setSchoolNameGoogleMap("unknown");
            }
            String phone = details.formattedPhoneNumber;
            if (phone != null && !"".equals(phone)) {
                schoolsInfo.setSchoolPhoneGoogleMap(details.formattedPhoneNumber);
            } else {
                schoolsInfo.setSchoolPhoneGoogleMap("unknown");
            }
            final URL url = details.url;
            if (url != null) {
                schoolsInfo.setGoogleMapUrlGoogleMap(details.url.toString());
            } else {
                schoolsInfo.setGoogleMapUrlGoogleMap("unknown");
            }
            final URL website = details.website;
            if (website != null) {
                schoolsInfo.setSchoolWebsiteGoogleMap(details.website.toString());
            } else {
                schoolsInfo.setSchoolWebsiteGoogleMap("unknown");
            }

            System.out.print(id + "|\t");
            System.out.print(name + "|\t");
            System.out.print(phone + "|\t");
            System.out.print(url + "|\t");
            System.out.print(website + "|\t");
//            schoolsInfoService.saveAndFlush(schoolsInfo);
            System.out.println("");
        }
//        final PlacesSearchResponse sd45 = PlacesApi.textSearchQuery(context, "Ridgeview Elementary").region("CA-BC").await();
//        for (PlacesSearchResult result : sd45.results) {
//            System.out.println(result);
//        }
    }

}

//[GeocodingResult
//  placeId=ChIJ4wBpSUZuhlQRceyv0ZTIAo8
//  [
//      Geometry: 49.34528120,-123.15036220 (ROOFTOP) bounds=null,
//      viewport=[49.34663018,-123.14901322, 49.34393222,-123.15171118]
//   ],
//  formattedAddress=1250 Chartwell Dr, West Vancouver, BC V7S 2R2, Canada,
//  types=[establishment, point_of_interest, school, secondary_school],
//  addressComponents=[
//      [AddressComponent: "1250" ("1250") (street_number)],
//      [AddressComponent: "Chartwell Drive" ("Chartwell Dr") (route)],
//      [AddressComponent: "West Vancouver" ("West Vancouver") (locality, political)],
//      [AddressComponent: "West Vancouver" ("West Vancouver") (administrative_area_level_3, political)],
//      [AddressComponent: "Metro Vancouver" ("Metro Vancouver") (administrative_area_level_2, political)],
//      [AddressComponent: "British Columbia" ("BC") (administrative_area_level_1, political)],
//      [AddressComponent: "Canada" ("CA") (country, political)],
//      [AddressComponent: "V7S 2R2" ("V7S 2R2") (postal_code)]
//  ]
//  ]

