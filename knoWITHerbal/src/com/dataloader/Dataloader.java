package com.dataloader;

import android.database.sqlite.SQLiteDatabase;

import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.ImagesModel;
import com.models.PlantModel;

public class Dataloader {

	public static void LoadPlants(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		PlantModel item1 = new PlantModel();
		item1.name = "Annatto";
		item1.availability = "Wild-Crafted";
		item1.common_names = "Abisrana (Ilk.), Aritana (Bik.),Balangbang (If.) , Inginga (Ig.), Kapal-kapal (Sul.), Karitana (Bis), Kokoeng (Bon.), Lapak-lapak (Sul.), Putputok (Bon.), Siempre viva, Angelica (Sp.) , Air plant (Engl.), Cathedral bells (Engl.), Miracle plant (Engl.), Mother of thousands (Engl.), Live-leaf-of-resurrection plant (Engl.), Lao di sheng gen (Chin.)";
		item1.properties = "Katakataka is an erect, more or less branched, smooth, succulent herb, 0.4 to 1.4 meters in height. Leaves are simple or pinnately compound, with the leaflets elliptic, usually about 10 centimeters long, thick, succulent, and scalloped margins. Plantlets grow along the notches of the leaf margins which can 		  develop while still attached to the plant or when detached, a fascinating characteristic that earns its name. Flowers are cylindric, and pendulous in a large, terminal panicle. Calyx is tubular, cylindric, inflated, brownish or purplish, 3.5 to 4 centimeters long. Corolla is tubular, about 5 centimeters long, inflated at the base, and then constricted, the exserted parts being reddish or purplish and thelobes tapering to a point. Fruit is a follicle with many seeds.";
		item1.scientific_name = "Bryophyllum pinnatum,Kalanchoe pinnata,Cotyledon pinnata";
		item1.usage = "Usage (Daghan kaau!)";
		item1.vernacular_names = "BRUNEI: Bendingin Serigen.BURMA: Yoekiyapinba.INDONESIA: Daun sejk, Buntiris, Sosor bebek.LAOS: Pount tay, Poun po.MALAYSIA: Sedingin, Seringing, Setawar padang.THAILAND: Benchachat, Ton tai bai pen, Khwum taai ngaai pen.VIETNAM: C[aa]y thu[oos]c b[or]ng, C[aa]y tr[uw] [owf]ng sinh, l[aj]c c[ij]a sinh c[aw]n";
		item1.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item1.pID);
		
		PlantModel item2 = new PlantModel();
		item2.name = "Gabi-gabihan";
		item2.availability = "Wild-crafted";
		item2.common_names = "Gabi-gabi (Bis.),Gabi-gabihan (Tag.),Kosol-kosol (Bis.),Payau-payau (Bis.),Arrowleaf false pickerelweed (Engl.),Arrow leaf pondweed (Engl.),Leaf pondweed (Engl.),Pickerel weed (Engl.)";
		item2.properties = "Gabi-gabihan is fast-growing perennial herb. Leaves are long-petioled. Petioles are stout, up to 60 centimeters long, sheathing below. Blade is broadly ovate, 10 to 30 centimeters long, the base prominently hastate, the sinus very broad, the lobes spreading and oblong-ovate. Inflorescence is spicate, many flowered, 4 to 5 centimeters long. Flowers are blue, about 1 centimeter long, the lower ones with elongated pedicels.";
		item2.scientific_name = "Monochoria dilatata,Monochoria hastaefolia,Monochoria hastata,Pontederia dilatata,Pontederia hastata,Jian ye yu Jiu huo";
		item2.usage = "Folkloric." +
				"- The leaves are used for poulticing boils after they have burst."+
				"- Juice of roots used for stomach pains, asthma, toothache."+
				"- Juice of leaves used for cough."+
				"- Used for insanity."+
				"- Root bark used for asthma."+
				"- Juice of leaves applied to boils. "+
				"- Rhizomes pounded in charcoal used for scurf."+
				"- In India, used in a herbal mixture to strengthen uterine tone."+
				"- In Bangladesh, used against diarrhea and dysentery; also, as an aphrodisiac";
		item2.vernacular_names = "BENGALI: Nukha." +
				"HINDI: Launkia";
		item2.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item2.pID);
		
		PlantModel item3 = new PlantModel();
		item3.name = "Arbor vitae";
		item3.availability = "Wild-crafted.Essential oils in the cybermarket";
		item3.common_names = "Biota (Engl.),Chinese arbor-vitae (Engl.),Oriental arbor-vitae (Engl.),Huang bai (Chin.)";
		item3.properties = "Oriental arbor-vitae is similar to Thuja occidentalis in general appearancve, but has upright cones and thickened scales. Branchlets are ramified in a vertical plane with both sides nearly alike. Female cones are globose and ovate. Seeds are wingless.";
		item3.scientific_name = "Platycladus orientalis,Platycladus stricta,Thuja chengii,Thuja orientalis,Ce bai";
		item3.usage = "Folkloric."+
		  "- No reported folkloric medicinal use in the Philippines."+
		  "- In Reunion, used mainly as antirheumatic: cones crushed and soaked in alcohol for 2 to 3 days, and the extract rubbed on painful joints. Decoction of small branches used for varicose veins, hemorrhoids, and menopausal problems. Also used for fever and to treat gastric ulcers."+ 
		  "- In Mauritius, decoction of branches and leaves used for throat inflammation, fever, influenza."+
		  "- In traditional Chinese medicine, leaves used as stomachic, refrigerant, diuretic, tonic and antipyretic."+
		  "- In Indo-China, ground leaves used as emmenagogue and antitussive; seeds as tonic, sedative, traquilizer, and aphrodisiac. Decoction of twigw used for dysentery, skin infections, and cough.";
		item3.vernacular_names = "CHINESE: Xiang bai, Bian bai, Bian gui, Xiang shu, Xiang ke shu."+
		  "CZECH: Túje východní, Zeravec východní."+
		  "DUTCH: Oosterse levensboom."+
		  "FRENCH: Thuya d'Orient, Thuya de Chine."+
		  "GERMANY: Morgenländischer Lebensbaum, Orientalischer Lebensbaum."+
		  "HUNGARY: Keletfa."+
		  "ITALIAN: Albero della vita, Tuia orientale."+
		  "KOREA: chuk paek namu."+
	 	  "PORTUGUESE: Biota-da-china."+
		  "RUSSIAN: Biota vostochnaya, Tuya vostochnaya."+
		  "SLOVAKIAN: Tuja východná."+
		  "SPANISH: Arbol de la vida, Arbol de la vida chino.";
		item3.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item3.pID);
		
		PlantModel item4 = new PlantModel();
		item4.name = "Five-leaved chaste tree";
		item4.availability = "Philippines";
		item4.common_names = "Common Names";
		item4.properties = "Properties";
		item4.scientific_name = "Sci. Name: Five-leaved chaste tree";
		item4.usage = "Usage";
		item4.vernacular_names = "Vernacular Name(s)";
		item4.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item4.pID);

		PlantModel item5 = new PlantModel();
		item5.name = "Gandarusa";
		item5.availability = "Philippines";
		item5.common_names = "Common Names";
		item5.properties = "Properties";
		item5.scientific_name = "Sci. Name: Gandarusa";
		item5.usage = "Usage";
		item5.vernacular_names = "Vernacular Name(s)";
		item5.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item5.pID);

		PlantModel item6 = new PlantModel();
		item6.name = "Heart of Jesus";
		item6.availability = "Philippines";
		item6.common_names = "Common Names";
		item6.properties = "Properties";
		item6.scientific_name = "Sci. Name: Heart of Jesus";
		item6.usage = "Usage";
		item6.vernacular_names = "Vernacular Name(s)";
		item6.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item6.pID);
		
		PlantModel item7 = new PlantModel();
		item7.name = "Japanese climbing fern";
		item7.availability = "Philippines";
		item7.common_names = "Common Names";
		item7.properties = "Properties";
		item7.scientific_name = "Sci. Name: Japanese climbing fern";
		item7.usage = "Usage";
		item7.vernacular_names = "Vernacular Name(s)";
		item7.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item7.pID);
		
		PlantModel item8 = new PlantModel();
		item8.name = "Mugwort";
		item8.availability = "Philippines";
		item8.common_names = "Common Names";
		item8.properties = "Properties";
		item8.scientific_name = "Sci. Name: Mugwort";
		item8.usage = "Usage";
		item8.vernacular_names = "Vernacular Name(s)";
		item8.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item8.pID);
		
		PlantModel item9 = new PlantModel();
		item9.name = "Philippine maidenhair";
		item9.availability = "Philippines";
		item9.common_names = "Common Names";
		item9.properties = "Properties";
		item9.scientific_name = "Sci. Name: Philippine maidenhair";
		item9.usage = "Usage";
		item9.vernacular_names = "Vernacular Name(s)";
		item9.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item9.pID);
		
		PlantModel item10 = new PlantModel();
		item10.name = "Latherleaf";
		item10.availability = "Philippines";
		item10.common_names = "Common Names";
		item10.properties = "Properties";
		item10.scientific_name = "Sci. Name: Latherleaf";
		item10.usage = "Usage";
		item10.vernacular_names = "Vernacular Name(s)";
		item10.imgUrls = Queries.getImageURLS(sqliteDB, dbHelper, item10.pID);
		
		Queries.InsertPlant(sqliteDB, dbHelper, item1);
		Queries.InsertPlant(sqliteDB, dbHelper, item2);
		Queries.InsertPlant(sqliteDB, dbHelper, item3);
		Queries.InsertPlant(sqliteDB, dbHelper, item4);
		Queries.InsertPlant(sqliteDB, dbHelper, item5);
		Queries.InsertPlant(sqliteDB, dbHelper, item6);
		Queries.InsertPlant(sqliteDB, dbHelper, item7);
		Queries.InsertPlant(sqliteDB, dbHelper, item8);
		Queries.InsertPlant(sqliteDB, dbHelper, item9);
		Queries.InsertPlant(sqliteDB, dbHelper, item10);
	}

	public static void LoadImages(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		for(int i=1;i<=4;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 1;
			item.url = "annatto_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 2;
			item.url = "arrowleaf_false_pickerelweed_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 3;
			item.url = "arbor_vitae_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 4;
			item.url = "areca_nut_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 5;
			item.url = "arabian_jasmine_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 6;
			item.url = "apricot_vine_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=4;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 7;
			item.url = "apple_of_sodom_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=2;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 8;
			item.url = "ant_plant_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=3;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 9;
			item.url = "angel's_trumpet_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		for(int i=1;i<=5;i++)
		{
			ImagesModel item = new ImagesModel();
			item.pID = 10;
			item.url = "amaranth_"+ i +".jpg";
			Queries.InsertImage(sqliteDB, dbHelper, item);
		}
		
		/*ImagesModel entry1 = new ImagesModel();
		entry1.pID = 9;
		entry1.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/5.jpg";
		
		ImagesModel entry2 = new ImagesModel();
		entry2.pID = 10;
		entry2.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/4.jpg";
		
		ImagesModel entry3 = new ImagesModel();
		entry3.pID = 11;
		entry3.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/3.jpg";
		
		ImagesModel entry4 = new ImagesModel();
		entry4.pID = 12;
		entry4.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/2.jpg";
		
		ImagesModel entry5 = new ImagesModel();
		entry5.pID = 13;
		entry5.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/1.jpg";
		
		ImagesModel entry6 = new ImagesModel();
		entry6.pID = 14;
		entry6.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/5.jpg";
		
		ImagesModel entry7 = new ImagesModel();
		entry7.pID = 15;
		entry7.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/3.jpg";
		
		ImagesModel entry8 = new ImagesModel();
		entry8.pID = 16;
		entry8.url = Environment.getExternalStorageDirectory() + "/knoWITHerbal/1.jpg";
		
		Queries.InsertImage(sqliteDB, dbHelper, entry1);
		Queries.InsertImage(sqliteDB, dbHelper, entry2);
		Queries.InsertImage(sqliteDB, dbHelper, entry3);
		Queries.InsertImage(sqliteDB, dbHelper, entry4);
		Queries.InsertImage(sqliteDB, dbHelper, entry5);
		Queries.InsertImage(sqliteDB, dbHelper, entry6);
		Queries.InsertImage(sqliteDB, dbHelper, entry7);
		Queries.InsertImage(sqliteDB, dbHelper, entry8);*/
		
		/*ImagesModel item;
		for(int i=1;i<=10;i++)
		{
			for(int j=1;j<=5;j++)
			{
				item = new ImagesModel();
				item.pID = i;
				String img = j +".jpg";
				item.url = img;
				Queries.InsertImage(sqliteDB, dbHelper, item);
			}
		}*/
	}
}
