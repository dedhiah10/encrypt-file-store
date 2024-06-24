package com.SL.SportyShoes.ProductController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.SL.SportyShoes.DatabaseComms.ProductRepository;
import com.SL.SportyShoes.DatabaseComms.UserRepository;
import com.SL.SportyShoes.Entities.Products;
import com.SL.SportyShoes.Entities.User;

import encrypt.decrypt.file.KeyClass;



@Entity
class ProdTime {
	@Id
	private int transactionId;
	private final int prodId;
	private final Date dateOfPurch;
	private final Time timeOfPurch;

	public ProdTime(int prodId, Date dateOfPurch, Time timeOfPurch) {
		super();
		this.prodId = prodId;
		this.dateOfPurch = dateOfPurch;
		this.timeOfPurch = timeOfPurch;
	}

	public int getProdId() {
		return prodId;
	}

	public Date getDateOfPurch() {
		return dateOfPurch;
	}

	public Time getTimeOfPurch() {
		return timeOfPurch;
	}

	@Override
	public String toString() {
		return "ProdTime [prodId=" + prodId + ", dateOfPurch=" + dateOfPurch + ", timeOfPurch=" + timeOfPurch + "]";
	}
}

@Controller
public class ProductController {
	@Autowired
	ProductRepository repopro;
	@Autowired
	UserRepository repousr;
	// Boolean exists;
	Map<Integer, String> usrprod = new HashMap<>();
	Map<Integer, List<ProdTime>> usrbuy = new HashMap<>();
	List<Products> prods;
	boolean flag = false;
	KeyClass kc = new KeyClass("AES");

	@RequestMapping("/")
	@GetMapping("/")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		if (!flag) {
			prods = repopro.findAll();
			flag = true;
		}
		
		ModelAndView mv = new ModelAndView("Index.jsp");
		Map<String, JSONArray> map = new HashMap<>();
		HttpSession session = req.getSession(false);
		JSONArray list = new JSONArray();
		for (int i = 0; i < prods.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Company", prods.get(i).getCompany());
			obj.put("Name", prods.get(i).getName());
			obj.put("Type", prods.get(i).getType());
			obj.put("Price", prods.get(i).getPrice());
			obj.put("Info", prods.get(i).getInfo());
			obj.put("pID", prods.get(i).getpID());
			list.put(obj);
		}
		map.put("abc", list);
		JSONArray list2 = new JSONArray();
		list2.put(0);
		map.put("usrAdd", list2);
		if (req.getParameter("usrAdd") != null) {
			if (req.getParameter("usrAdd").equalsIgnoreCase("true")) {
				list2.remove(0);
				list2.put(true);
				map.put("usrAdd", list2);
			} else if (req.getParameter("usrAdd").equalsIgnoreCase("false")) {
				list2.remove(0);
				list2.put(false);
				map.put("usrAdd", list2);
			}
		}
		JSONArray list3 = new JSONArray();
		list3.put(0);
		map.put("usrFound", list3);
		if (session != null && session.getAttribute("usrFound") != null) {
			if ((boolean) session.getAttribute("usrFound")) {
				list3.remove(0);
				list3.put(true);
				list3.put(session.getAttribute("uname"));
				map.put("usrFound", list3);
			} else if (!(boolean) session.getAttribute("usrFound")) {
				list3.remove(0);
				list3.put(false);
				map.put("usrFound", list3);
			}
		} else if (session != null && session.getAttribute("uname") != null) {
			list3.remove(0);
			list3.put(true);
			list3.put(session.getAttribute("uname"));
			map.put("usrFound", list3);
		}
		JSONArray list4 = new JSONArray();
		list4.put(0);
		map.put("adminRole", list4);
		// System.out.println("Printed!");
		if (session != null && session.getAttribute("admin") != null) {
			// System.out.println((boolean) session.getAttribute("adminRole"));
			if (!(boolean) session.getAttribute("admin")) {
				list4.remove(0);
				list4.put(false);
				map.put("adminRole", list4);
			} else if ((boolean) session.getAttribute("admin")) {
				list4.remove(0);
				list4.put(true);
				map.put("adminRole", list4);
			}
		}
		mv.addAllObjects(map);
		return mv;
	}

	@RequestMapping("/LoginController")
	@GetMapping("/LoginController")
	public String login(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			return "redirect:/";
		} else {
			return "Login.jsp";
		}
	}

	@PostMapping("/UserChecker")
	public String checker(HttpServletRequest req, HttpServletResponse resp) {
		boolean flag = false;
		List<User> usrlist = repousr.findByName(req.getParameter("uname").toLowerCase());
		for (User usr : usrlist) {
			if (usr.getPassword().equals(req.getParameter("pword"))) {
				HttpSession session = req.getSession(true);
				if (usrprod.get(usr.getuID()) == null) {
					usrprod.put(usr.getuID(), "0");
					usrbuy.put(usr.getuID(), new ArrayList<ProdTime>());
				}
				session.setAttribute("uname", usr.getName());
				session.setAttribute("uID", usr.getuID());
				if (req.getParameter("admin") != null) {
					session.setAttribute("adminRole", true);
					if (usr.isRole()) {
						session.setAttribute("admin", true);
					} else {
						session.setAttribute("admin", false);
					}
				} else {
					session.setAttribute("adminRole", false);
					session.setAttribute("admin", false);
				}
				flag = true;
				session.setAttribute("usrFound", flag);
			}
		}
		return "redirect:/";
	}

	@RequestMapping("/NewUser")
	@GetMapping("/NewUser")
	public String newuser(HttpServletRequest req) {
		return "UserForm.jsp";
	}

	@PostMapping("/UserAdder")
	public String userAdd(User user, HttpServletRequest req, HttpServletResponse resp) {
		user.setName(user.getName().toLowerCase());
		if (req.getParameter("roleID") != null) {
			if (req.getParameter("roleID").equals("Truly")) {
				user.setRole(true);
			}
			;
		}
		boolean exists = update(user);
		return "redirect:/?usrAdd=" + exists;
	}

	boolean update(User user) {
		// long time = System.currentTimeMillis();
		boolean exists = false;
		List<User> usrlist = repousr.findByNumber(user.getNumber());
		/*
		 * Iterator<User> itr = usrlist.listIterator(); while(itr.hasNext()) { User usr
		 * = itr.next(); if( usr.getCountry().equalsIgnoreCase(user.getCountry()) &&
		 * usr.getName().equalsIgnoreCase(user.getName()) &&
		 * usr.getDateOfBirth().equals(user.getDateOfBirth()) ) {exists = true;
		 * System.out.println("Entered");} }
		 */
		for (User usr : usrlist) {
			if (usr.getCountry().equalsIgnoreCase(user.getCountry()) && usr.getName().equalsIgnoreCase(user.getName())
					&& usr.getDateOfBirth().equals(user.getDateOfBirth())) {
				exists = true;
				break;
			} else {
				exists = false;
			}
		}
		if (exists == false) {
			repousr.save(user);
		}
		// System.out.println(System.currentTimeMillis() - time);
		return exists;
	}

	@GetMapping("/SignOut")
	@RequestMapping("/SignOut")
	public String signout(HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/";
	}

	@RequestMapping("/CartAdder")
	@GetMapping("/CartAdder")
	public String cartadd(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			usrprod.replace((Integer) session.getAttribute("uID"),
					(usrprod.get(session.getAttribute("uID")) + "_" + req.getParameter("pID")));
			// System.out.println((usrprod.get(session.getAttribute("uID"))));
			return "redirect:/";
		} else {
			return "redirect:/LoginController";
		}
	}

	@RequestMapping("/CartManager")
	@GetMapping("/CartManager")
	public String cartman(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			return "redirect:/CartMaker";
		} else {
			return "redirect:/LoginController";
		}
	}

	@RequestMapping("/CartMaker")
	@GetMapping("/CartMaker")
	public ModelAndView cartmake(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		String[] x = usrprod.get(session.getAttribute("uID")).split("_");
		ModelAndView mv = new ModelAndView("CartItems.jsp");
		List<Products> prods = new ArrayList<Products>();
		for (int i = 1; i < x.length; i++) {
			// System.out.println(x[i]);
			Products prod1 = repopro.findById(Integer.parseInt(x[i])).orElse(null);
			if (prod1 != null) {
				prods.add(prod1);
			}
		}
		Map<String, JSONArray> map = new HashMap<>();
		JSONArray list = new JSONArray();
		for (int i = 0; i < prods.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Company", prods.get(i).getCompany());
			obj.put("Name", prods.get(i).getName());
			obj.put("Type", prods.get(i).getType());
			obj.put("Price", prods.get(i).getPrice());
			obj.put("Info", prods.get(i).getInfo());
			obj.put("pID", prods.get(i).getpID());
			list.put(obj);
		}
		JSONArray list3 = new JSONArray();
		list3.put(0);
		map.put("usrFound", list3);
		if (req.getParameter("usrFound") != null) {
			if (req.getParameter("usrFound").equalsIgnoreCase("true")) {
				list3.remove(0);
				list3.put(true);
				list3.put(session.getAttribute("uname"));
				map.put("usrFound", list3);
			} else if (req.getParameter("usrFound").equalsIgnoreCase("false")) {
				list3.remove(0);
				list3.put(false);
				map.put("usrFound", list3);
			}
		} else if (session != null && session.getAttribute("uname") != null) {
			list3.remove(0);
			list3.put(true);
			list3.put(session.getAttribute("uname"));
			map.put("usrFound", list3);
		}
		JSONArray list4 = new JSONArray();
		list4.put(0);
		map.put("adminRole", list4);
		// System.out.println("Printed!");
		if (session != null && session.getAttribute("admin") != null) {
			// System.out.println((boolean) session.getAttribute("adminRole"));
			if (!(boolean) session.getAttribute("admin")) {
				list4.remove(0);
				list4.put(false);
				map.put("adminRole", list4);
			} else if ((boolean) session.getAttribute("admin")) {
				list4.remove(0);
				list4.put(true);
				map.put("adminRole", list4);
			}
		}
		map.put("abc", list);
		mv.addAllObjects(map);
		return mv;
	}

	@GetMapping("/CartRemover")
	public String cartrem(HttpServletRequest req) {
		boolean flag = false;
		HttpSession session = req.getSession(false);
		String[] x = usrprod.get(session.getAttribute("uID")).split("_");
		String newx = new String("");
		// System.out.println(usrprod.get(session.getAttribute("uID")));
		for (int i = 0; i < x.length; i++) {
			// System.out.println(x[i]);
			// System.out.println(newx);
			if (flag == true) {
				newx += (x[i] + "_");
			} else if (flag == false && !(x[i].equals(req.getParameter("rpID")))) {
				newx += (x[i] + "_");
			} else if ((x[i].equals(req.getParameter("rpID")))) {
				flag = true;
			} // System.out.println(newx);
		}
		usrprod.replace((Integer) session.getAttribute("uID"), newx.substring(0, newx.length() - 1));
		req.getParameter("pID");
		return "redirect:/CartManager";
	}

	@GetMapping("/CheckoutCart")
	public String buycart(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			String[] x = usrprod.get(session.getAttribute("uID")).split("_");
			List<ProdTime> y = usrbuy.get(session.getAttribute("uID"));
			Date a = new Date(System.currentTimeMillis());
			Time b = new Time(System.currentTimeMillis());
			for (int i = 1; i < x.length; i++) {
				y.add(new ProdTime(Integer.parseInt(x[i]), a, b));
			}
			usrbuy.put((Integer) session.getAttribute("uID"), y);
			usrprod.put((Integer) session.getAttribute("uID"), "0_");
		}
		return "redirect:/CartManager";
	}

	@GetMapping("/BuyAdder")
	@RequestMapping("/BuyAdder")
	public String buyprod(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			List<ProdTime> x = usrbuy.get(session.getAttribute("uID"));
			System.out.println("Item bought");
			x.add(new ProdTime(Integer.parseInt(req.getParameter("pID")), new Date(System.currentTimeMillis()),
					new Time(System.currentTimeMillis())));
			usrbuy.replace((Integer) session.getAttribute("uID"), x);
		}
		// System.out.println(usrbuy.get(session.getAttribute("uID")));
		return "redirect:/";
	}

	@GetMapping("/AccountDetails")
	@RequestMapping("/AccountDetails")
	public ModelAndView accDet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			ModelAndView mv = new ModelAndView("MyAccount.jsp");
			Map<String, JSONArray> map = new HashMap<>();
			List<ProdTime> usrprodtime = new ArrayList<ProdTime>();
			usrprodtime = usrbuy.get((Integer) session.getAttribute("uID"));
			JSONArray list = new JSONArray();
			for (ProdTime a : usrprodtime) {
				JSONObject obj = new JSONObject();
				Products p = prods.get(a.getProdId() - 1);
				obj.put("Date", a.getDateOfPurch());
				obj.put("Time", a.getDateOfPurch());
				obj.put("Company", p.getCompany());
				obj.put("Name", p.getName());
				obj.put("Type", p.getType());
				obj.put("Price", p.getPrice());
				obj.put("Info", p.getInfo());
				obj.put("pID", p.getpID());
				list.put(obj);
			}
			map.put("usrbuy", list);
			JSONArray list1 = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("uname", session.getAttribute("uname"));
			User usr = repousr.findById((int) session.getAttribute("uID")).orElse(null);
			obj.put("Country", usr.getCountry());
			String x = "" + usr.getNumber();
			obj.put("Number", "*****" + x.substring(5));
			obj.put("DoB", usr.getDateOfBirth());
			obj.put("ZipCode", usr.getZipCode());
			list1.put(obj);
			map.put("UserDetails", list1);
			mv.addAllObjects(map);
			return mv;
		} else {
			return index(req, resp);
		}
	}

	@GetMapping("/AdminRights")
	@RequestMapping("/AdminRights")
	public ModelAndView adminRights(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("uname") != null) {
			if ((boolean) session.getAttribute("adminRole") && (boolean) session.getAttribute("admin")) {
				return adminRightsMaker(req);
			} else {
				return index(req, resp);
			}
		}
		return index(req, resp);
	}

	// @GetMapping("/AdminRightsPageMaker") @RequestMapping("/AdminRightsPageMaker")
	public ModelAndView adminRightsMaker(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("Transactions.jsp");
		Map<String, JSONArray> map = new HashMap<>();
		JSONArray listUsers = new JSONArray();
		List<User> usrlist = repousr.findAll();
		for (User usr : usrlist) {
			JSONObject userInfo = new JSONObject();
			userInfo.put("Name", usr.getName());
			userInfo.put("Number", "*****" + ("" + usr.getNumber()).substring(5));
			userInfo.put("Country", usr.getCountry());
			userInfo.put("Zip Code", usr.getZipCode());
			List<ProdTime> usrprodtime = usrbuy.get(usr.getuID());
			JSONArray usrbuylist = new JSONArray();
			if (usrprodtime != null) {
				for (ProdTime a : usrprodtime) {
					JSONObject obj = new JSONObject();
					Products p = prods.get(a.getProdId() - 1);
					obj.put("Date", a.getDateOfPurch());
					obj.put("Time", a.getDateOfPurch());
					obj.put("Company", p.getCompany());
					obj.put("Name", p.getName());
					obj.put("Type", p.getType());
					obj.put("Price", p.getPrice());
					obj.put("Info", p.getInfo());
					obj.put("pID", p.getpID());
					usrbuylist.put(obj);
					userInfo.put("Prods", usrbuylist);
				}
			}
			listUsers.put(userInfo);
		}
		map.put("users", listUsers);
		mv.addAllObjects(map);
		// System.out.println(usrbuy.get(2));
		return mv;
	}
	
	@PostMapping("/EncUpload")
	public String encup(@RequestParam("fileUp") MultipartFile file, HttpServletRequest req) {
		long timeTaken = System.nanoTime();
		long size = file.getSize(); 
		try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());) {
			System.out.println(file.getOriginalFilename());
			//new File("enc\\"+file.getName()).createNewFile();
			kc.encdecWithAES(Cipher.ENCRYPT_MODE, bis, new File("enc/"+file.getOriginalFilename()));
		} 
		catch (Exception e) {e.printStackTrace();}
		System.out.println("That Encryption took:" + (System.nanoTime() - timeTaken)/1000000 + "ms for file size: " + size/1024 + "kB");
		return "redirect:/LoginController";
	}
	
	@GetMapping(value = "/DecDown/{fileName}",  produces = MediaType.ALL_VALUE)
	public void decdown(HttpServletResponse resp, @PathVariable("fileName") String fileName) {
		long timeTaken = System.nanoTime();
		File file = new File("enc/"+fileName);
		if(file.exists()) {
			try {
				long size = file.length();
				resp.setContentType("application/octet-stream");
				resp.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				kc.encdecWithAES(Cipher.DECRYPT_MODE, file, new BufferedOutputStream(resp.getOutputStream()));
				System.out.println("That Encryption took:" + (System.nanoTime() - timeTaken)/1000000 + "ms for file size: " + size/1024  + "kB");
				//resp.setContentLength((int) file.length());
			} catch(Exception e) {e.printStackTrace();}
		}
	}
}
