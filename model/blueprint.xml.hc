<?xml version="1.0" encoding="UTF-8"?>
<application name="HC">
	<data-model>
		<entity name="Profile"> 
			<attribute name="firstname" datatype="string" searchable="yes"/>
			<attribute name="lastname" datatype="string" />
			<attribute name="gender" datatype="string" />
			<attribute name="birthday" datatype="date"/>
			<attribute name="photo" datatype="file"/>
			<attribute name="email" datatype="string"/>
			<attribute name="phone" datatype="string"/>
			<attribute name="userId" datatype="string" searchable="yes"/>
			<attribute name="healthLog" datatype="HealthLog" relationtype="1-n" parent="yes"/>
			<attribute name="hospitalNumber" datatype="HospitalNumber" relationtype="1-n" parent="yes"/>
		</entity>
		
		<entity name="HealthLog" flexibleprop="yes"> 
			<attribute name="profile" datatype="Profile" relationtype="n-1" parent="no" />
			<attribute name="logDate" datatype="datetime" />
		</entity>

		<entity name="HealthCategory"> 
			<attribute name="code" datatype="string" searchable="yes" />
			<attribute name="nameTH" datatype="string" searchable="yes" />
			<attribute name="nameEN" datatype="string" searchable="yes"/>
			<attribute name="icon" datatype="string" />
			<attribute name="healthMeasure" datatype="HealthMeasure" relationtype="1-n" parent="yes"/>
		</entity>
		
		<entity name="HealthMeasure"> 
			<attribute name="code" datatype="string" searchable="yes"/>
			<attribute name="nameTH" datatype="string" searchable="yes"/>
			<attribute name="nameEN" datatype="string" searchable="yes" />
			<attribute name="DescriptionTH" datatype="string" />
			<attribute name="DescriptionEN" datatype="string" />
			<attribute name="unit" datatype="string" />
			<attribute name="high1Value" datatype="double" defaultvalue="9999"/>
			<attribute name="high2Value" datatype="double" defaultvalue="9999"/>
			<attribute name="high3Value" datatype="double" defaultvalue="9999"/>
			<attribute name="high1LabelTH" datatype="string"/>
			<attribute name="high2LabelTH" datatype="string"/>
			<attribute name="high3LabelTH" datatype="string"/>
			<attribute name="high1LabelEN" datatype="string"/>
			<attribute name="high2LabelEN" datatype="string"/>
			<attribute name="high3LabelEN" datatype="string"/>
			<attribute name="low1Value" datatype="double" defaultvalue="-9999"/>
			<attribute name="low2Value" datatype="double" defaultvalue="-9999"/>
			<attribute name="low3Value" datatype="double" defaultvalue="-9999"/>
			<attribute name="low1LabelTH" datatype="string"/>
			<attribute name="low2LabelTH" datatype="string"/>
			<attribute name="low3LabelTH" datatype="string"/>
			<attribute name="low1LabelEN" datatype="string"/>
			<attribute name="low2LabelEN" datatype="string"/>
			<attribute name="low3LabelEN" datatype="string"/>
			<attribute name="healthCategory" datatype="HealthCategory" relationtype="n-1" parent="no" eagerfetch="yes" />
			<attribute name="healthGuide" datatype="HealthGuide" relationtype="1-n" parent="yes"/>
		</entity>
		
		<entity name="HealthGuideType"> 
			<attribute name="code" datatype="string" searchable="yes"/>
			<attribute name="icon" datatype="string" />
			<attribute name="healthGuide" datatype="HealthGuide" relationtype="1-n" parent="yes"/>

		</entity>
		
		<entity name="Consumption"> 
			<attribute name="code" datatype="string" searchable="yes"/>
			<attribute name="name" datatype="string" />
			<attribute name="description" datatype="string" />
			<attribute name="calories" datatype="double" />
			<attribute name="protein" datatype="double" />
			<attribute name="fiber" datatype="double" />
			<attribute name="fat" datatype="double" />
			<attribute name="carbohydrates" datatype="double" />
			<attribute name="sugars" datatype="double" />
			<attribute name="mealType" datatype="string" />
			<attribute name="unitCount" datatype="integer" />
			<attribute name="logDate" datatype="datetime" />
			<attribute name="profile" datatype="Profile" relationtype="n-1" parent="no" />
		</entity>
		
		<entity name="Food"> 
			<attribute name="code" datatype="string" searchable="yes"/>
			<attribute name="name" datatype="string" searchable="yes"/>
			<attribute name="description" datatype="string" />
			<attribute name="calories" datatype="double" />
			<attribute name="protein" datatype="double" />
			<attribute name="fiber" datatype="double" />
			<attribute name="fat" datatype="double" />
			<attribute name="carbohydrates" datatype="double" />
			<attribute name="sugars" datatype="double" />
			<attribute name="unitName" datatype="string" />
		</entity>
		
		<entity name="HealthGuide"> 
			<attribute name="high1TxtTH" datatype="string"/>
			<attribute name="high1TxtEN" datatype="string"/>
			<attribute name="high2TxtTH" datatype="string"/>
			<attribute name="high2TxtEN" datatype="string"/>
			<attribute name="high3TxtTH" datatype="string"/>
			<attribute name="high3TxtEN" datatype="string"/>
			<attribute name="low1TxtEN" datatype="string"/>
			<attribute name="low1TxtTH" datatype="string"/>
			<attribute name="low2TxtEN" datatype="string"/>
			<attribute name="low2TxtTH" datatype="string"/>
			<attribute name="low3TxtEN" datatype="string"/>
			<attribute name="low3TxtTH" datatype="string"/>
			<attribute name="upTxtEN" datatype="string"/>
			<attribute name="upTxtTH" datatype="string"/>
			<attribute name="downTxtEN" datatype="string"/>
			<attribute name="downTxtTH" datatype="string"/>
			<attribute name="healthGuideType" datatype="HealthGuideType" relationtype="n-1" parent="no" eagerfetch="yes" />
			<attribute name="healthMeasure" datatype="HealthMeasure" relationtype="n-1" parent="no" eagerfetch="yes" />
		</entity>
		
		<entity name="Hospital" > 
			<attribute name="nameTH" datatype="string" searchable="yes"/>
			<attribute name="nameEN" datatype="string" searchable="yes" />
			<attribute name="hospitalEPR" datatype="HospitalEPR" relationtype="1-n" parent="yes"/>
		</entity>
		
		<entity name="HospitalEPR" > 
			<attribute name="number" datatype="string" searchable="yes" />
			<attribute name="profile" datatype="Profile" relationtype="n-1" parent="no" />
			<attribute name="hospital" datatype="Hospital" relationtype="n-1" parent="no" eagerfetch="yes" />
		</entity>

	</data-model>
</application>
