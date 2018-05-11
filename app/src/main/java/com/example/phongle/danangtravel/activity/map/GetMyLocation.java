package com.example.phongle.danangtravel.activity.map;


public class GetMyLocation {
//    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
//    private LatLng mMyLatlng;
//    private Activity mActivity;
//    private Context mContext;
//
//    public GetMyLocation(Activity mActivity, Context mContext) {
//        this.mActivity = mActivity;
//        this.mContext = mContext;
//    }
//
//    private void askPermissionsAndGetMyLocation() {
//        // if API >23 ask permission
//        if (Build.VERSION.SDK_INT >= 23) {
//            int accessCoarsePermission
//                    = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
//            int accessFinePermission
//                    = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
//
//            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
//                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
//
//                // Các quyền cần người dùng cho phép.
//                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION};
//                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
//                ActivityCompat.requestPermissions(mActivity, permissions,
//                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
//                return;
//            }
//        }
//        getMyLocation();
//    }
//
//    // Tìm một nhà cung cấp vị trị hiện thời đang được mở.
//    private String getEnabledLocationProvider() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        // Tiêu chí để tìm một nhà cung cấp vị trí.
//        Criteria criteria = new Criteria();
//        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
//        // ==> "gps", "network",...
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//        boolean enabled = locationManager.isProviderEnabled(bestProvider);
//        if (!enabled) {
//            Toast.makeText(mContext, "No location provider enabled!", Toast.LENGTH_LONG).show();
//            Log.i("xxx", "No location provider enabled!");
//            return null;
//        }
//        return bestProvider;
//    }
//
//    // Khi người dùng trả lời yêu cầu cấp quyền (cho phép hoặc từ chối).
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //
//        switch (requestCode) {
//            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {
//                // Chú ý: Nếu yêu cầu bị bỏ qua, mảng kết quả là rỗng.
//                if (grantResults.length > 1
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(mContext, "Permission granted!", Toast.LENGTH_LONG).show();
//                    // Hiển thị vị trí hiện thời trên bản đồ.
//                    this.getMyLocation();
//                }
//                // Hủy bỏ hoặc từ chối.
//                else {
//                    Toast.makeText(mContext, "Permission denied!", Toast.LENGTH_LONG).show();
//                }
//                break;
//            }
//        }
//    }
//
//    private void getMyLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        String locationProvider = this.getEnabledLocationProvider();
//
//        if (locationProvider == null) {
//            return;
//        }
//        // Millisecond
//        final long MIN_TIME_BW_UPDATES = 1000;
//        // Met
//        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
//        Location myLocation = null;
//        try {
//
//            // Đoạn code nay cần người dùng cho phép (Hỏi ở trên ***).
//            locationManager.requestLocationUpdates(
//                    locationProvider,
//                    MIN_TIME_BW_UPDATES,
//                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
//            // Lấy ra vị trí.
//            myLocation = locationManager
//                    .getLastKnownLocation(locationProvider);
//
//        }
//        // Với Android API >= 23 catch SecurityException.
//        catch (SecurityException e) {
//            Toast.makeText(mContext, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e("xxx", "Show My Location Error:" + e.getMessage());
//            e.printStackTrace();
//            return;
//        }
//        if (myLocation != null) {
//            mMyLatlng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//        }
//
//    }
}
