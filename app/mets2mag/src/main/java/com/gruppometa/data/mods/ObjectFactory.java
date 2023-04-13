//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gruppometa.data.mods package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Extension_QNAME = new QName("http://www.loc.gov/mods/v3", "extension");
    private final static QName _Identifier_QNAME = new QName("http://www.loc.gov/mods/v3", "identifier");
    private final static QName _Language_QNAME = new QName("http://www.loc.gov/mods/v3", "language");
    private final static QName _ShelfLocator_QNAME = new QName("http://www.loc.gov/mods/v3", "shelfLocator");
    private final static QName _SubLocation_QNAME = new QName("http://www.loc.gov/mods/v3", "subLocation");
    private final static QName _ElectronicLocator_QNAME = new QName("http://www.loc.gov/mods/v3", "electronicLocator");
    private final static QName _HoldingExternal_QNAME = new QName("http://www.loc.gov/mods/v3", "holdingExternal");
    private final static QName _Etal_QNAME = new QName("http://www.loc.gov/mods/v3", "etal");
    private final static QName _DisplayForm_QNAME = new QName("http://www.loc.gov/mods/v3", "displayForm");
    private final static QName _Affiliation_QNAME = new QName("http://www.loc.gov/mods/v3", "affiliation");
    private final static QName _Description_QNAME = new QName("http://www.loc.gov/mods/v3", "description");
    private final static QName _NameIdentifier_QNAME = new QName("http://www.loc.gov/mods/v3", "nameIdentifier");
    private final static QName _Note_QNAME = new QName("http://www.loc.gov/mods/v3", "note");
    private final static QName _DateIssued_QNAME = new QName("http://www.loc.gov/mods/v3", "dateIssued");
    private final static QName _DateCreated_QNAME = new QName("http://www.loc.gov/mods/v3", "dateCreated");
    private final static QName _DateCaptured_QNAME = new QName("http://www.loc.gov/mods/v3", "dateCaptured");
    private final static QName _DateValid_QNAME = new QName("http://www.loc.gov/mods/v3", "dateValid");
    private final static QName _DateModified_QNAME = new QName("http://www.loc.gov/mods/v3", "dateModified");
    private final static QName _CopyrightDate_QNAME = new QName("http://www.loc.gov/mods/v3", "copyrightDate");
    private final static QName _Edition_QNAME = new QName("http://www.loc.gov/mods/v3", "edition");
    private final static QName _Issuance_QNAME = new QName("http://www.loc.gov/mods/v3", "issuance");
    private final static QName _Frequency_QNAME = new QName("http://www.loc.gov/mods/v3", "frequency");
    private final static QName _Number_QNAME = new QName("http://www.loc.gov/mods/v3", "number");
    private final static QName _Caption_QNAME = new QName("http://www.loc.gov/mods/v3", "caption");
    private final static QName _Title_QNAME = new QName("http://www.loc.gov/mods/v3", "title");
    private final static QName _Date_QNAME = new QName("http://www.loc.gov/mods/v3", "date");
    private final static QName _ReformattingQuality_QNAME = new QName("http://www.loc.gov/mods/v3", "reformattingQuality");
    private final static QName _InternetMediaType_QNAME = new QName("http://www.loc.gov/mods/v3", "internetMediaType");
    private final static QName _DigitalOrigin_QNAME = new QName("http://www.loc.gov/mods/v3", "digitalOrigin");
    private final static QName _RecordContentSource_QNAME = new QName("http://www.loc.gov/mods/v3", "recordContentSource");
    private final static QName _RecordCreationDate_QNAME = new QName("http://www.loc.gov/mods/v3", "recordCreationDate");
    private final static QName _RecordChangeDate_QNAME = new QName("http://www.loc.gov/mods/v3", "recordChangeDate");
    private final static QName _LanguageOfCataloging_QNAME = new QName("http://www.loc.gov/mods/v3", "languageOfCataloging");
    private final static QName _RecordOrigin_QNAME = new QName("http://www.loc.gov/mods/v3", "recordOrigin");
    private final static QName _DescriptionStandard_QNAME = new QName("http://www.loc.gov/mods/v3", "descriptionStandard");
    private final static QName _RecordInfoNote_QNAME = new QName("http://www.loc.gov/mods/v3", "recordInfoNote");
    private final static QName _Topic_QNAME = new QName("http://www.loc.gov/mods/v3", "topic");
    private final static QName _Geographic_QNAME = new QName("http://www.loc.gov/mods/v3", "geographic");
    private final static QName _ExtraTerrestrialArea_QNAME = new QName("http://www.loc.gov/mods/v3", "extraTerrestrialArea");
    private final static QName _Continent_QNAME = new QName("http://www.loc.gov/mods/v3", "continent");
    private final static QName _Country_QNAME = new QName("http://www.loc.gov/mods/v3", "country");
    private final static QName _Province_QNAME = new QName("http://www.loc.gov/mods/v3", "province");
    private final static QName _State_QNAME = new QName("http://www.loc.gov/mods/v3", "state");
    private final static QName _Territory_QNAME = new QName("http://www.loc.gov/mods/v3", "territory");
    private final static QName _County_QNAME = new QName("http://www.loc.gov/mods/v3", "county");
    private final static QName _City_QNAME = new QName("http://www.loc.gov/mods/v3", "city");
    private final static QName _Island_QNAME = new QName("http://www.loc.gov/mods/v3", "island");
    private final static QName _Scale_QNAME = new QName("http://www.loc.gov/mods/v3", "scale");
    private final static QName _Projection_QNAME = new QName("http://www.loc.gov/mods/v3", "projection");
    private final static QName _Coordinates_QNAME = new QName("http://www.loc.gov/mods/v3", "coordinates");
    private final static QName _CartographicExtension_QNAME = new QName("http://www.loc.gov/mods/v3", "cartographicExtension");
    private final static QName _Occupation_QNAME = new QName("http://www.loc.gov/mods/v3", "occupation");
    private final static QName _SubTitle_QNAME = new QName("http://www.loc.gov/mods/v3", "subTitle");
    private final static QName _PartNumber_QNAME = new QName("http://www.loc.gov/mods/v3", "partNumber");
    private final static QName _PartName_QNAME = new QName("http://www.loc.gov/mods/v3", "partName");
    private final static QName _Start_QNAME = new QName("http://www.loc.gov/mods/v3", "start");
    private final static QName _End_QNAME = new QName("http://www.loc.gov/mods/v3", "end");
    private final static QName _Total_QNAME = new QName("http://www.loc.gov/mods/v3", "total");
    private final static QName _List_QNAME = new QName("http://www.loc.gov/mods/v3", "list");
    private final static QName _SubjectTitleInfo_QNAME = new QName("http://www.loc.gov/mods/v3", "titleInfo");
    private final static QName _SubjectName_QNAME = new QName("http://www.loc.gov/mods/v3", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gruppometa.data.mods
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CopyInformation }
     * 
     */
    public CopyInformation createCopyInformation() {
        return new CopyInformation();
    }

    /**
     * Create an instance of {@link Mods }
     * 
     */
    public Mods createMods() {
        return new Mods();
    }

    /**
     * Create an instance of {@link Abstract }
     * 
     */
    public Abstract createAbstract() {
        return new Abstract();
    }

    /**
     * Create an instance of {@link StringPlusLanguage }
     * 
     */
    public StringPlusLanguage createStringPlusLanguage() {
        return new StringPlusLanguage();
    }

    /**
     * Create an instance of {@link AccessCondition }
     * 
     */
    public AccessCondition createAccessCondition() {
        return new AccessCondition();
    }

    /**
     * Create an instance of {@link ExtensionDefinition }
     * 
     */
    public ExtensionDefinition createExtensionDefinition() {
        return new ExtensionDefinition();
    }

    /**
     * Create an instance of {@link Classification }
     * 
     */
    public Classification createClassification() {
        return new Classification();
    }

    /**
     * Create an instance of {@link StringPlusLanguagePlusAuthority }
     * 
     */
    public StringPlusLanguagePlusAuthority createStringPlusLanguagePlusAuthority() {
        return new StringPlusLanguagePlusAuthority();
    }

    /**
     * Create an instance of {@link Genre }
     * 
     */
    public Genre createGenre() {
        return new Genre();
    }

    /**
     * Create an instance of {@link IdentifierDefinition }
     * 
     */
    public IdentifierDefinition createIdentifierDefinition() {
        return new IdentifierDefinition();
    }

    /**
     * Create an instance of {@link LanguageDefinition }
     * 
     */
    public LanguageDefinition createLanguageDefinition() {
        return new LanguageDefinition();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link PhysicalLocation }
     * 
     */
    public PhysicalLocation createPhysicalLocation() {
        return new PhysicalLocation();
    }

    /**
     * Create an instance of {@link Url }
     * 
     */
    public Url createUrl() {
        return new Url();
    }

    /**
     * Create an instance of {@link HoldingSimple }
     * 
     */
    public HoldingSimple createHoldingSimple() {
        return new HoldingSimple();
    }

    /**
     * Create an instance of {@link Form }
     * 
     */
    public Form createForm() {
        return new Form();
    }

    /**
     * Create an instance of {@link CopyInformation.Note }
     * 
     */
    public CopyInformation.Note createCopyInformationNote() {
        return new CopyInformation.Note();
    }

    /**
     * Create an instance of {@link EnumerationAndChronology }
     * 
     */
    public EnumerationAndChronology createEnumerationAndChronology() {
        return new EnumerationAndChronology();
    }

    /**
     * Create an instance of {@link ItemIdentifier }
     * 
     */
    public ItemIdentifier createItemIdentifier() {
        return new ItemIdentifier();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link NamePart }
     * 
     */
    public NamePart createNamePart() {
        return new NamePart();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link RoleTerm }
     * 
     */
    public RoleTerm createRoleTerm() {
        return new RoleTerm();
    }

    /**
     * Create an instance of {@link AlternativeName }
     * 
     */
    public AlternativeName createAlternativeName() {
        return new AlternativeName();
    }

    /**
     * Create an instance of {@link NoteDefinition }
     * 
     */
    public NoteDefinition createNoteDefinition() {
        return new NoteDefinition();
    }

    /**
     * Create an instance of {@link OriginInfo }
     * 
     */
    public OriginInfo createOriginInfo() {
        return new OriginInfo();
    }

    /**
     * Create an instance of {@link Place }
     * 
     */
    public Place createPlace() {
        return new Place();
    }

    /**
     * Create an instance of {@link PlaceTerm }
     * 
     */
    public PlaceTerm createPlaceTerm() {
        return new PlaceTerm();
    }

    /**
     * Create an instance of {@link Publisher }
     * 
     */
    public Publisher createPublisher() {
        return new Publisher();
    }

    /**
     * Create an instance of {@link StringPlusLanguagePlusSupplied }
     * 
     */
    public StringPlusLanguagePlusSupplied createStringPlusLanguagePlusSupplied() {
        return new StringPlusLanguagePlusSupplied();
    }

    /**
     * Create an instance of {@link DateDefinition }
     * 
     */
    public DateDefinition createDateDefinition() {
        return new DateDefinition();
    }

    /**
     * Create an instance of {@link DateOther }
     * 
     */
    public DateOther createDateOther() {
        return new DateOther();
    }

    /**
     * Create an instance of {@link Part }
     * 
     */
    public Part createPart() {
        return new Part();
    }

    /**
     * Create an instance of {@link Detail }
     * 
     */
    public Detail createDetail() {
        return new Detail();
    }

    /**
     * Create an instance of {@link ExtentDefinition }
     * 
     */
    public ExtentDefinition createExtentDefinition() {
        return new ExtentDefinition();
    }

    /**
     * Create an instance of {@link Text }
     * 
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link PhysicalDescription }
     * 
     */
    public PhysicalDescription createPhysicalDescription() {
        return new PhysicalDescription();
    }

    /**
     * Create an instance of {@link Extent }
     * 
     */
    public Extent createExtent() {
        return new Extent();
    }

    /**
     * Create an instance of {@link PhysicalDescriptionNote }
     * 
     */
    public PhysicalDescriptionNote createPhysicalDescriptionNote() {
        return new PhysicalDescriptionNote();
    }

    /**
     * Create an instance of {@link RecordInfo }
     * 
     */
    public RecordInfo createRecordInfo() {
        return new RecordInfo();
    }

    /**
     * Create an instance of {@link RecordIdentifier }
     * 
     */
    public RecordIdentifier createRecordIdentifier() {
        return new RecordIdentifier();
    }

    /**
     * Create an instance of {@link RelatedItem }
     * 
     */
    public RelatedItem createRelatedItem() {
        return new RelatedItem();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link Temporal }
     * 
     */
    public Temporal createTemporal() {
        return new Temporal();
    }

    /**
     * Create an instance of {@link SubjectTitleInfoDefinition }
     * 
     */
    public SubjectTitleInfoDefinition createSubjectTitleInfoDefinition() {
        return new SubjectTitleInfoDefinition();
    }

    /**
     * Create an instance of {@link SubjectNameDefinition }
     * 
     */
    public SubjectNameDefinition createSubjectNameDefinition() {
        return new SubjectNameDefinition();
    }

    /**
     * Create an instance of {@link GeographicCode }
     * 
     */
    public GeographicCode createGeographicCode() {
        return new GeographicCode();
    }

    /**
     * Create an instance of {@link HierarchicalGeographic }
     * 
     */
    public HierarchicalGeographic createHierarchicalGeographic() {
        return new HierarchicalGeographic();
    }

    /**
     * Create an instance of {@link HierarchicalPart }
     * 
     */
    public HierarchicalPart createHierarchicalPart() {
        return new HierarchicalPart();
    }

    /**
     * Create an instance of {@link Region }
     * 
     */
    public Region createRegion() {
        return new Region();
    }

    /**
     * Create an instance of {@link CitySection }
     * 
     */
    public CitySection createCitySection() {
        return new CitySection();
    }

    /**
     * Create an instance of {@link Area }
     * 
     */
    public Area createArea() {
        return new Area();
    }

    /**
     * Create an instance of {@link Cartographics }
     * 
     */
    public Cartographics createCartographics() {
        return new Cartographics();
    }

    /**
     * Create an instance of {@link TableOfContents }
     * 
     */
    public TableOfContents createTableOfContents() {
        return new TableOfContents();
    }

    /**
     * Create an instance of {@link TargetAudience }
     * 
     */
    public TargetAudience createTargetAudience() {
        return new TargetAudience();
    }

    /**
     * Create an instance of {@link TitleInfo }
     * 
     */
    public TitleInfo createTitleInfo() {
        return new TitleInfo();
    }

    /**
     * Create an instance of {@link NonSort }
     * 
     */
    public NonSort createNonSort() {
        return new NonSort();
    }

    /**
     * Create an instance of {@link TypeOfResource }
     * 
     */
    public TypeOfResource createTypeOfResource() {
        return new TypeOfResource();
    }

    /**
     * Create an instance of {@link ModsCollection }
     * 
     */
    public ModsCollection createModsCollection() {
        return new ModsCollection();
    }

    /**
     * Create an instance of {@link LanguageTerm }
     * 
     */
    public LanguageTerm createLanguageTerm() {
        return new LanguageTerm();
    }

    /**
     * Create an instance of {@link ScriptTerm }
     * 
     */
    public ScriptTerm createScriptTerm() {
        return new ScriptTerm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "extension")
    public JAXBElement<ExtensionDefinition> createExtension(ExtensionDefinition value) {
        return new JAXBElement<ExtensionDefinition>(_Extension_QNAME, ExtensionDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdentifierDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "identifier")
    public JAXBElement<IdentifierDefinition> createIdentifier(IdentifierDefinition value) {
        return new JAXBElement<IdentifierDefinition>(_Identifier_QNAME, IdentifierDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LanguageDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "language")
    public JAXBElement<LanguageDefinition> createLanguage(LanguageDefinition value) {
        return new JAXBElement<LanguageDefinition>(_Language_QNAME, LanguageDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "shelfLocator")
    public JAXBElement<StringPlusLanguage> createShelfLocator(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_ShelfLocator_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "subLocation")
    public JAXBElement<StringPlusLanguage> createSubLocation(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_SubLocation_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "electronicLocator")
    public JAXBElement<StringPlusLanguage> createElectronicLocator(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_ElectronicLocator_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "holdingExternal")
    public JAXBElement<ExtensionDefinition> createHoldingExternal(ExtensionDefinition value) {
        return new JAXBElement<ExtensionDefinition>(_HoldingExternal_QNAME, ExtensionDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "etal")
    public JAXBElement<StringPlusLanguage> createEtal(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Etal_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "displayForm")
    public JAXBElement<StringPlusLanguage> createDisplayForm(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_DisplayForm_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "affiliation")
    public JAXBElement<StringPlusLanguage> createAffiliation(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Affiliation_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "description")
    public JAXBElement<StringPlusLanguage> createDescription(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Description_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdentifierDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "nameIdentifier")
    public JAXBElement<IdentifierDefinition> createNameIdentifier(IdentifierDefinition value) {
        return new JAXBElement<IdentifierDefinition>(_NameIdentifier_QNAME, IdentifierDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoteDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoteDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "note")
    public JAXBElement<NoteDefinition> createNote(NoteDefinition value) {
        return new JAXBElement<NoteDefinition>(_Note_QNAME, NoteDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "dateIssued")
    public JAXBElement<DateDefinition> createDateIssued(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_DateIssued_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "dateCreated")
    public JAXBElement<DateDefinition> createDateCreated(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_DateCreated_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "dateCaptured")
    public JAXBElement<DateDefinition> createDateCaptured(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_DateCaptured_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "dateValid")
    public JAXBElement<DateDefinition> createDateValid(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_DateValid_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "dateModified")
    public JAXBElement<DateDefinition> createDateModified(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_DateModified_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "copyrightDate")
    public JAXBElement<DateDefinition> createCopyrightDate(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_CopyrightDate_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusSupplied }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusSupplied }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "edition")
    public JAXBElement<StringPlusLanguagePlusSupplied> createEdition(StringPlusLanguagePlusSupplied value) {
        return new JAXBElement<StringPlusLanguagePlusSupplied>(_Edition_QNAME, StringPlusLanguagePlusSupplied.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssuanceDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IssuanceDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "issuance")
    public JAXBElement<IssuanceDefinition> createIssuance(IssuanceDefinition value) {
        return new JAXBElement<IssuanceDefinition>(_Issuance_QNAME, IssuanceDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "frequency")
    public JAXBElement<StringPlusLanguagePlusAuthority> createFrequency(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_Frequency_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "number")
    public JAXBElement<StringPlusLanguage> createNumber(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Number_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "caption")
    public JAXBElement<StringPlusLanguage> createCaption(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Caption_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "title")
    public JAXBElement<StringPlusLanguage> createTitle(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Title_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "date")
    public JAXBElement<DateDefinition> createDate(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_Date_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReformattingQualityDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReformattingQualityDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "reformattingQuality")
    public JAXBElement<ReformattingQualityDefinition> createReformattingQuality(ReformattingQualityDefinition value) {
        return new JAXBElement<ReformattingQualityDefinition>(_ReformattingQuality_QNAME, ReformattingQualityDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "internetMediaType")
    public JAXBElement<StringPlusLanguage> createInternetMediaType(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_InternetMediaType_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DigitalOriginDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DigitalOriginDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "digitalOrigin")
    public JAXBElement<DigitalOriginDefinition> createDigitalOrigin(DigitalOriginDefinition value) {
        return new JAXBElement<DigitalOriginDefinition>(_DigitalOrigin_QNAME, DigitalOriginDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "recordContentSource")
    public JAXBElement<StringPlusLanguagePlusAuthority> createRecordContentSource(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_RecordContentSource_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "recordCreationDate")
    public JAXBElement<DateDefinition> createRecordCreationDate(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_RecordCreationDate_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "recordChangeDate")
    public JAXBElement<DateDefinition> createRecordChangeDate(DateDefinition value) {
        return new JAXBElement<DateDefinition>(_RecordChangeDate_QNAME, DateDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LanguageDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "languageOfCataloging")
    public JAXBElement<LanguageDefinition> createLanguageOfCataloging(LanguageDefinition value) {
        return new JAXBElement<LanguageDefinition>(_LanguageOfCataloging_QNAME, LanguageDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "recordOrigin")
    public JAXBElement<StringPlusLanguage> createRecordOrigin(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_RecordOrigin_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "descriptionStandard")
    public JAXBElement<StringPlusLanguagePlusAuthority> createDescriptionStandard(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_DescriptionStandard_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoteDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoteDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "recordInfoNote")
    public JAXBElement<NoteDefinition> createRecordInfoNote(NoteDefinition value) {
        return new JAXBElement<NoteDefinition>(_RecordInfoNote_QNAME, NoteDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "topic")
    public JAXBElement<StringPlusLanguagePlusAuthority> createTopic(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_Topic_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "geographic")
    public JAXBElement<StringPlusLanguagePlusAuthority> createGeographic(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_Geographic_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "extraTerrestrialArea")
    public JAXBElement<HierarchicalPart> createExtraTerrestrialArea(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_ExtraTerrestrialArea_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "continent")
    public JAXBElement<HierarchicalPart> createContinent(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_Continent_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "country")
    public JAXBElement<HierarchicalPart> createCountry(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_Country_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "province")
    public JAXBElement<StringPlusLanguage> createProvince(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Province_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "state")
    public JAXBElement<HierarchicalPart> createState(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_State_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "territory")
    public JAXBElement<HierarchicalPart> createTerritory(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_Territory_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "county")
    public JAXBElement<HierarchicalPart> createCounty(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_County_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "city")
    public JAXBElement<HierarchicalPart> createCity(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_City_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "island")
    public JAXBElement<HierarchicalPart> createIsland(HierarchicalPart value) {
        return new JAXBElement<HierarchicalPart>(_Island_QNAME, HierarchicalPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "scale")
    public JAXBElement<StringPlusLanguage> createScale(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Scale_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "projection")
    public JAXBElement<StringPlusLanguage> createProjection(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Projection_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "coordinates")
    public JAXBElement<StringPlusLanguage> createCoordinates(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Coordinates_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExtensionDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "cartographicExtension")
    public JAXBElement<ExtensionDefinition> createCartographicExtension(ExtensionDefinition value) {
        return new JAXBElement<ExtensionDefinition>(_CartographicExtension_QNAME, ExtensionDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "occupation")
    public JAXBElement<StringPlusLanguagePlusAuthority> createOccupation(StringPlusLanguagePlusAuthority value) {
        return new JAXBElement<StringPlusLanguagePlusAuthority>(_Occupation_QNAME, StringPlusLanguagePlusAuthority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "subTitle")
    public JAXBElement<StringPlusLanguage> createSubTitle(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_SubTitle_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "partNumber")
    public JAXBElement<StringPlusLanguage> createPartNumber(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_PartNumber_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "partName")
    public JAXBElement<StringPlusLanguage> createPartName(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_PartName_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "start")
    public JAXBElement<StringPlusLanguage> createStart(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_Start_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "end")
    public JAXBElement<StringPlusLanguage> createEnd(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_End_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "total")
    public JAXBElement<BigInteger> createTotal(BigInteger value) {
        return new JAXBElement<BigInteger>(_Total_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "list")
    public JAXBElement<StringPlusLanguage> createList(StringPlusLanguage value) {
        return new JAXBElement<StringPlusLanguage>(_List_QNAME, StringPlusLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectTitleInfoDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubjectTitleInfoDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "titleInfo", scope = Subject.class)
    public JAXBElement<SubjectTitleInfoDefinition> createSubjectTitleInfo(SubjectTitleInfoDefinition value) {
        return new JAXBElement<SubjectTitleInfoDefinition>(_SubjectTitleInfo_QNAME, SubjectTitleInfoDefinition.class, Subject.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectNameDefinition }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubjectNameDefinition }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/mods/v3", name = "name", scope = Subject.class)
    public JAXBElement<SubjectNameDefinition> createSubjectName(SubjectNameDefinition value) {
        return new JAXBElement<SubjectNameDefinition>(_SubjectName_QNAME, SubjectNameDefinition.class, Subject.class, value);
    }

}
