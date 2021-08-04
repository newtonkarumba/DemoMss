package com.systech.mss.config;

import com.systech.mss.domain.User;
import com.systech.mss.domain.UserSponsor;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.dto.ClientOrdinal;
import com.systech.mss.service.dto.Ordinal;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static final String MMM_d_yyyy = "MMM d, yyyy";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final int TIMEOUT = 300 * 1000;
    private static final String ROWS = "rows";
    private static final String BOTH = "BOTH";
    private static final String MSS = "PORTAL";
    private static final String XI = "FUNDMASTER";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HTTP_POST = "POST";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "success";
    private static final String HTTP_GET = "GET";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String HTTPS = "https";
    private static final String[] names = {"MEMBER_NO", "NATIONAL", "VOTER", "PASSPORT", "PENNO", "DRIVER", "STAFF", "PIN", "EMAIL", "NHIF", "PHONE", "MEMBER_NO", "MEMBER_ID"};
    private static final String[] ux_names = {"Member Number", "National ID", "Voter Number",  "Passport Number", "Pension Number", "Driver Licence Number", "Staff Number", "PIN Number", "Email Address", "NHIF Number", "Phone Number", "Member ID", "MBIO ID"};
    private static final String[] ordinal_keys = {"memberNo", "idNo", "", "", "", "", "", "pinNo", "email", "", "", "memberNo", "id"};
    /**
     *
     */

    public static final long serialVersionUID = 1L;

    @Inject
    UserRepository userRepository;

    public String getOrdinalKey(String ordinal)
    {
        for(int i = 0; i < names.length; i ++)
        {
            if(names[i] == ordinal)
                return ordinal_keys[i];
        }
        return null;
    }
    public long toLong(Object o)
    {
        try {
            return Long.valueOf(o.toString());
        } catch (NullPointerException npe) {
            return 0;
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
    public int toInteger(Object o){
        try {
            return Integer.parseInt(o.toString());
        } catch (NullPointerException npe) {
            return 0;
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public BigDecimal toBigDecimal(Object o)
    {
        try {
            String obj = o.toString();
            return new BigDecimal(obj.replaceAll(",", ""));
        } catch (NullPointerException npe) {
            return BigDecimal.ZERO;
        } catch (NumberFormatException nfe) {
            return BigDecimal.ZERO;
        }
    }

    public String toString(Object o)
    {
        try {
            return o.toString();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public Date dateFromString(String date, String ft)
    {

        DateFormat format = new SimpleDateFormat(ft, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    public Date toDate(Object date)
    {

        String strDate = date.toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {

            return formatter.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public  boolean isValidPhone(String phone) {
        Matcher matcher = null;
        if (phone.startsWith("+")){
            matcher = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$", Pattern.CASE_INSENSITIVE) .matcher(phone);

            return matcher.find();


        }else if (phone.startsWith("0")){
            matcher = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE) .matcher(phone);
            return matcher.find();

        }

        return false;

    }
    public Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTime().getTime());
    }

    public String encrypt(String string)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException nsae) {
            return string;
        }

    }

    public String hash(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }


    private String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public String result(boolean status, String message, JSONObject json)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put(Helper.SUCCESS, status);
            obj.put(Helper.MESSAGE, message);
            obj.put("data", json != null ? json.toString() : json);
            return obj.toString();
        } catch (JSONException je) {
            return null;
        }
    }

    public void createFolderIfNotExists(String folder_name) {
        File f = new File(folder_name);
        if(!f.exists()){
            try{
                f.mkdirs();
            }catch(SecurityException se){
            }catch(Exception se){
            }
        }else{
        }
    }

    /*public List<Ordinal> getOrdinals()
    {
        List<Ordinal> ordinals = new ArrayList<>();
        for(int i = 0; i < names.length; i ++)
        {
            Ordinal ordinal = new Ordinal();
            ordinal.setName(ux_names[i]);
            ordinal.setCode(names[i]);
        }
        return ordinals;
    }*/

    public List<Ordinal> getOrdinals()
    {
        Ordinal ordinal = new Ordinal(Long.valueOf("1").longValue(), "MEMBER_NO", "Member Number");
        Ordinal ordinal2 = new Ordinal(Long.valueOf("2").longValue(), "NATIONAL", "National ID");
        Ordinal ordinal3 = new Ordinal(Long.valueOf("3").longValue(), "VOTER", "Voter Number");
        Ordinal ordinal4 = new Ordinal(Long.valueOf("4").longValue(), "PASSPORT", "Passport Number");
        Ordinal ordinal5 = new Ordinal(Long.valueOf("5").longValue(), "PENNO", "Pension Number");
        Ordinal ordinal6 = new Ordinal(Long.valueOf("6").longValue(), "DRIVER", "Driver Licence Number");
        Ordinal ordinal7 = new Ordinal(Long.valueOf("7").longValue(), "STAFF", "Staff Number");
        Ordinal ordinal8 = new Ordinal(Long.valueOf("8").longValue(), "TAX_NUMBER", "PIN Number");
        Ordinal ordinal9 = new Ordinal(Long.valueOf("9").longValue(), "EMAIL", "Email Address");
        Ordinal ordinal10 = new Ordinal(Long.valueOf("10").longValue(), "NHIF", "NHIF Number");
        Ordinal ordinal11 = new Ordinal(Long.valueOf("11").longValue(), "PHONE", "Phone Number");
        Ordinal ordinal12 = new Ordinal(Long.valueOf("12").longValue(), "MEMBER_NO", "Member ID");
        Ordinal ordinal13 = new Ordinal(Long.valueOf("13").longValue(), "PIN", "PIN Number");
        Ordinal ordinal14 = new Ordinal(Long.valueOf("14").longValue(), "PENSIONER_NO", "Pensioner Number");
        Ordinal ordinal15 = new Ordinal(Long.valueOf("15").longValue(), "EMPLOYER_ID","Employer Number");
        List<Ordinal> ordinals = new ArrayList<>();
        ordinals.add(ordinal);
        ordinals.add(ordinal2);
        ordinals.add(ordinal3);
        ordinals.add(ordinal4);
        ordinals.add(ordinal5);
        ordinals.add(ordinal6);
        ordinals.add(ordinal7);
        ordinals.add(ordinal8);
        ordinals.add(ordinal9);
        ordinals.add(ordinal10);
        ordinals.add(ordinal11);
        ordinals.add(ordinal12);
        ordinals.add(ordinal13);
        ordinals.add(ordinal14);
        ordinals.add(ordinal15);
        return ordinals;
    }
    public List<ClientOrdinal> getClientOrdinals()

    //ETL", "UAPUG", "UAPKE", "LAPFUND", "CBK", "BOU", "BRITAM", "KP", "KENGEN
    {
        ClientOrdinal clientOrdinal1= new ClientOrdinal(Long.valueOf("1").longValue(), "ETL", "ENTERPRISE TRUSTEES LIMITED");
        ClientOrdinal clientOrdinal2 = new ClientOrdinal(Long.valueOf("2").longValue(), "UAPUG", "UAP UGANDA");
        ClientOrdinal clientOrdinal3 = new ClientOrdinal(Long.valueOf("3").longValue(), "UAPKE", "UAP KENYA");
        ClientOrdinal clientOrdinal4 = new ClientOrdinal(Long.valueOf("5").longValue(), "LAPFUND", "LAPFUND KENYA");
        ClientOrdinal clientOrdinal5= new ClientOrdinal(Long.valueOf("6").longValue(), "CBK", "CBK");
        ClientOrdinal clientOrdinal6 = new ClientOrdinal(Long.valueOf("7").longValue(), "BOU", "BOU");
        ClientOrdinal clientOrdinal7 = new ClientOrdinal(Long.valueOf("8").longValue(), "BRITAM", "BRITAM");
        ClientOrdinal clientOrdinal8 = new ClientOrdinal(Long.valueOf("9").longValue(), "KP", "KP");
        ClientOrdinal clientOrdinal9 = new ClientOrdinal(Long.valueOf("9").longValue(), "KENGEN", "KENGEN");
        List<ClientOrdinal> clientOrdinals = new ArrayList<>();
        clientOrdinals.add(clientOrdinal1);
        clientOrdinals.add(clientOrdinal2);
        clientOrdinals.add(clientOrdinal3);
        clientOrdinals.add(clientOrdinal4);
        clientOrdinals.add(clientOrdinal5);
        clientOrdinals.add(clientOrdinal6);
        clientOrdinals.add(clientOrdinal7);
        clientOrdinals.add(clientOrdinal8);
        clientOrdinals.add(clientOrdinal9);


        return clientOrdinals;
    }

    public JSONObject response(boolean status, String message)
    {
        try {
            return new JSONObject().put("success", status).put("message", message);
        } catch (JSONException je) {
            return null;
        }
    }

    public String format_no(double amount)
    {
        if(amount > 0)
        {
            DecimalFormat formatter = new DecimalFormat("#,###.00");

            return formatter.format(amount);
        }
        else
            return String.valueOf(amount);
    }



    public String humanReadableDate(String date_string)

    {
        Date compdate = null;
        DateFormat format = new SimpleDateFormat(Helper.YYYY_MM_DD, Locale.ENGLISH);
        try {
            try {
                compdate = format.parse(date_string);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            return formatter.format(compdate);
        }
        catch (Exception ex)
        {
            return date_string;
        }

    }

    private double round(double value) {

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public BigDecimal toBigDecimal(double value)
    {
        return BigDecimal.valueOf(value);
    }


    public boolean isManager(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(Constants.MANAGER_PROFILE) != null && session.getAttribute(Constants.MANAGER_PROFILE).equals(Constants.MANAGER);
    }

    public String shorterUUID(String uuid, int index)
    {
        try {
            String[] uuids = uuid.split("-");
            return uuids[index].toUpperCase();
        } catch (IndexOutOfBoundsException iOOB) {
            return uuid.toUpperCase();
        } catch (NullPointerException npe) {
            return uuid.toUpperCase();
        }
    }

    public String[] listProfiles()
    {
        return new String[]{"MEMBER", "ADMINISTRATOR", "SPONSOR", "TRUSTEE", "AGENT", "CUSTODIAN", "CUSTOMER_RELATIONSHIP_MANAGER", "CUSTOMER_RELATIONSHIP_EXECUTIVE", "FUND_MANAGER", "PENSIONER","PRINCIPAL_OFFICER"};
    }

    public String[] listClients()


    {
        return new String[]{"ETL","UAPUG", "UAPKE", "LAPFUND", "CBK", "BOU", "BRITAM", "KP", "KENGEN"};
    }

    public boolean isManagerial(String profile)
    {
        String[] profiles = listProfiles();
        for (String prof : profiles)
            if (prof.equals(profile))
                return true;
        return false;
    }


    public String randomNumber() {
        String randomNumber="";
        //generating a rendom number
        Random random=new Random();
        for(int i=0;i<6;i++){
            int max=6;
            int min=0;
            int randNo= random.nextInt((max - min )+ 1);
            randomNumber=randomNumber+randNo;

        }
        return randomNumber;
    }


    public UserSponsor checkUserBySmsCode(UserSponsor user, String code){
        if (userRepository.findOneByActivationKey(code)!=null){
            user.setSmsActivationCode(randomNumber());
        }else  {
            user.setSmsActivationCode(code);
        }
        return user;

    }
    public String formatMessage(String message, String name,String activationCode ){
        String msg1=message.replace("name",name);
        return msg1.replace("code",activationCode);

    }
}
