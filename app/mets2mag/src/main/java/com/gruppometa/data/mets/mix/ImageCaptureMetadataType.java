//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.mix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ImageCaptureMetadataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ImageCaptureMetadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SourceInformation" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="sourceType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="SourceID" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="sourceIDType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="sourceIDValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SourceSize" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="SourceXDimension" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="sourceXDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="sourceXDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="SourceYDimension" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="sourceYDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="sourceYDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="SourceZDimension" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="sourceZDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="sourceZDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="GeneralCaptureInformation" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dateTimeCreated" type="{http://www.loc.gov/mix/v20}typeOfDateType" minOccurs="0"/&gt;
 *                   &lt;element name="imageProducer" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="captureDevice" type="{http://www.loc.gov/mix/v20}typeOfCaptureDeviceType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ScannerCapture" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="scannerManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="ScannerModel" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="scannerModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="scannerModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="scannerModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="MaximumOpticalResolution" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="xOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                             &lt;element name="yOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                             &lt;element name="opticalResolutionUnit" type="{http://www.loc.gov/mix/v20}typeOfOpticalResolutionUnitType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="scannerSensor" type="{http://www.loc.gov/mix/v20}typeOfScannerSensorType" minOccurs="0"/&gt;
 *                   &lt;element name="ScanningSystemSoftware" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="scanningSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="scanningSoftwareVersionNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DigitalCameraCapture" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="digitalCameraManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="DigitalCameraModel" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="digitalCameraModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="digitalCameraModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="digitalCameraModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="cameraSensor" type="{http://www.loc.gov/mix/v20}typeOfCameraSensorType" minOccurs="0"/&gt;
 *                   &lt;element name="CameraCaptureSettings" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ImageData" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="fNumber" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="exposureTime" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="exposureProgram" type="{http://www.loc.gov/mix/v20}typeOfExposureProgramType" minOccurs="0"/&gt;
 *                                       &lt;element name="spectralSensitivity" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                       &lt;element name="isoSpeedRatings" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                                       &lt;element name="oECF" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="exifVersion" type="{http://www.loc.gov/mix/v20}typeOfExifVersionType" minOccurs="0"/&gt;
 *                                       &lt;element name="shutterSpeedValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="apertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="brightnessValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="exposureBiasValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="maxApertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="SubjectDistance" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="distance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
 *                                                 &lt;element name="MinMaxDistance" minOccurs="0"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
 *                                                           &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="meteringMode" type="{http://www.loc.gov/mix/v20}typeOfMeteringModeType" minOccurs="0"/&gt;
 *                                       &lt;element name="lightSource" type="{http://www.loc.gov/mix/v20}typeOfLightSourceType" minOccurs="0"/&gt;
 *                                       &lt;element name="flash" type="{http://www.loc.gov/mix/v20}typeOfFlashType" minOccurs="0"/&gt;
 *                                       &lt;element name="focalLength" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
 *                                       &lt;element name="flashEnergy" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="backLight" type="{http://www.loc.gov/mix/v20}typeOfBackLightType" minOccurs="0"/&gt;
 *                                       &lt;element name="exposureIndex" type="{http://www.loc.gov/mix/v20}typeOfPositiveRealType" minOccurs="0"/&gt;
 *                                       &lt;element name="sensingMethod" type="{http://www.loc.gov/mix/v20}typeOfSensingMethodType" minOccurs="0"/&gt;
 *                                       &lt;element name="cfaPattern" type="{http://www.loc.gov/mix/v20}integerType" minOccurs="0"/&gt;
 *                                       &lt;element name="autoFocus" type="{http://www.loc.gov/mix/v20}typeOfAutoFocusType" minOccurs="0"/&gt;
 *                                       &lt;element name="PrintAspectRatio" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="xPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                                 &lt;element name="yPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="GPSData" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="gpsVersionID" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLatitudeRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="GPSLatitude" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="gpsLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLongitudeRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="GPSLongitude" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="gpsAltitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsAltitudeRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsAltitude" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsTimeStamp" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsSatellites" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsStatus" type="{http://www.loc.gov/mix/v20}typeOfgpsStatusType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsMeasureMode" type="{http://www.loc.gov/mix/v20}typeOfgpsMeasureModeType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDOP" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsSpeedRef" type="{http://www.loc.gov/mix/v20}typeOfgpsSpeedRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsSpeed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsTrackRef" type="{http://www.loc.gov/mix/v20}typeOfgpsTrackRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsTrack" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsImgDirectionRef" type="{http://www.loc.gov/mix/v20}typeOfgpsImgDirectionRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsImgDirection" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsMapDatum" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDestLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLatitudeRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="GPSDestLatitude" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="gpsDestLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLongitudeRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="GPSDestLongitude" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="gpsDestBearingRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestBearingRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDestBearing" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDestDistanceRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestDistanceRefType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDestDistance" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsProcessingMethod" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsAreaInformation" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDateStamp" type="{http://www.loc.gov/mix/v20}dateType" minOccurs="0"/&gt;
 *                                       &lt;element name="gpsDifferential" type="{http://www.loc.gov/mix/v20}typeOfgpsDifferentialType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="orientation" type="{http://www.loc.gov/mix/v20}typeOfOrientationType" minOccurs="0"/&gt;
 *         &lt;element name="methodology" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageCaptureMetadataType", propOrder = {
    "sourceInformation",
    "generalCaptureInformation",
    "scannerCapture",
    "digitalCameraCapture",
    "orientation",
    "methodology"
})
public class ImageCaptureMetadataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "SourceInformation")
    protected ImageCaptureMetadataType.SourceInformation sourceInformation;
    @XmlElement(name = "GeneralCaptureInformation")
    protected ImageCaptureMetadataType.GeneralCaptureInformation generalCaptureInformation;
    @XmlElement(name = "ScannerCapture")
    protected ImageCaptureMetadataType.ScannerCapture scannerCapture;
    @XmlElement(name = "DigitalCameraCapture")
    protected ImageCaptureMetadataType.DigitalCameraCapture digitalCameraCapture;
    protected TypeOfOrientationType orientation;
    protected StringType methodology;

    /**
     * Recupera il valore della proprietà sourceInformation.
     * 
     * @return
     *     possible object is
     *     {@link ImageCaptureMetadataType.SourceInformation }
     *     
     */
    public ImageCaptureMetadataType.SourceInformation getSourceInformation() {
        return sourceInformation;
    }

    /**
     * Imposta il valore della proprietà sourceInformation.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCaptureMetadataType.SourceInformation }
     *     
     */
    public void setSourceInformation(ImageCaptureMetadataType.SourceInformation value) {
        this.sourceInformation = value;
    }

    /**
     * Recupera il valore della proprietà generalCaptureInformation.
     * 
     * @return
     *     possible object is
     *     {@link ImageCaptureMetadataType.GeneralCaptureInformation }
     *     
     */
    public ImageCaptureMetadataType.GeneralCaptureInformation getGeneralCaptureInformation() {
        return generalCaptureInformation;
    }

    /**
     * Imposta il valore della proprietà generalCaptureInformation.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCaptureMetadataType.GeneralCaptureInformation }
     *     
     */
    public void setGeneralCaptureInformation(ImageCaptureMetadataType.GeneralCaptureInformation value) {
        this.generalCaptureInformation = value;
    }

    /**
     * Recupera il valore della proprietà scannerCapture.
     * 
     * @return
     *     possible object is
     *     {@link ImageCaptureMetadataType.ScannerCapture }
     *     
     */
    public ImageCaptureMetadataType.ScannerCapture getScannerCapture() {
        return scannerCapture;
    }

    /**
     * Imposta il valore della proprietà scannerCapture.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCaptureMetadataType.ScannerCapture }
     *     
     */
    public void setScannerCapture(ImageCaptureMetadataType.ScannerCapture value) {
        this.scannerCapture = value;
    }

    /**
     * Recupera il valore della proprietà digitalCameraCapture.
     * 
     * @return
     *     possible object is
     *     {@link ImageCaptureMetadataType.DigitalCameraCapture }
     *     
     */
    public ImageCaptureMetadataType.DigitalCameraCapture getDigitalCameraCapture() {
        return digitalCameraCapture;
    }

    /**
     * Imposta il valore della proprietà digitalCameraCapture.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCaptureMetadataType.DigitalCameraCapture }
     *     
     */
    public void setDigitalCameraCapture(ImageCaptureMetadataType.DigitalCameraCapture value) {
        this.digitalCameraCapture = value;
    }

    /**
     * Recupera il valore della proprietà orientation.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfOrientationType }
     *     
     */
    public TypeOfOrientationType getOrientation() {
        return orientation;
    }

    /**
     * Imposta il valore della proprietà orientation.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfOrientationType }
     *     
     */
    public void setOrientation(TypeOfOrientationType value) {
        this.orientation = value;
    }

    /**
     * Recupera il valore della proprietà methodology.
     * 
     * @return
     *     possible object is
     *     {@link StringType }
     *     
     */
    public StringType getMethodology() {
        return methodology;
    }

    /**
     * Imposta il valore della proprietà methodology.
     * 
     * @param value
     *     allowed object is
     *     {@link StringType }
     *     
     */
    public void setMethodology(StringType value) {
        this.methodology = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="digitalCameraManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="DigitalCameraModel" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="digitalCameraModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="digitalCameraModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="digitalCameraModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="cameraSensor" type="{http://www.loc.gov/mix/v20}typeOfCameraSensorType" minOccurs="0"/&gt;
     *         &lt;element name="CameraCaptureSettings" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ImageData" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="fNumber" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                             &lt;element name="exposureTime" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                             &lt;element name="exposureProgram" type="{http://www.loc.gov/mix/v20}typeOfExposureProgramType" minOccurs="0"/&gt;
     *                             &lt;element name="spectralSensitivity" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                             &lt;element name="isoSpeedRatings" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                             &lt;element name="oECF" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="exifVersion" type="{http://www.loc.gov/mix/v20}typeOfExifVersionType" minOccurs="0"/&gt;
     *                             &lt;element name="shutterSpeedValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="apertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="brightnessValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="exposureBiasValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="maxApertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="SubjectDistance" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="distance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
     *                                       &lt;element name="MinMaxDistance" minOccurs="0"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
     *                                                 &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="meteringMode" type="{http://www.loc.gov/mix/v20}typeOfMeteringModeType" minOccurs="0"/&gt;
     *                             &lt;element name="lightSource" type="{http://www.loc.gov/mix/v20}typeOfLightSourceType" minOccurs="0"/&gt;
     *                             &lt;element name="flash" type="{http://www.loc.gov/mix/v20}typeOfFlashType" minOccurs="0"/&gt;
     *                             &lt;element name="focalLength" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
     *                             &lt;element name="flashEnergy" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="backLight" type="{http://www.loc.gov/mix/v20}typeOfBackLightType" minOccurs="0"/&gt;
     *                             &lt;element name="exposureIndex" type="{http://www.loc.gov/mix/v20}typeOfPositiveRealType" minOccurs="0"/&gt;
     *                             &lt;element name="sensingMethod" type="{http://www.loc.gov/mix/v20}typeOfSensingMethodType" minOccurs="0"/&gt;
     *                             &lt;element name="cfaPattern" type="{http://www.loc.gov/mix/v20}integerType" minOccurs="0"/&gt;
     *                             &lt;element name="autoFocus" type="{http://www.loc.gov/mix/v20}typeOfAutoFocusType" minOccurs="0"/&gt;
     *                             &lt;element name="PrintAspectRatio" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="xPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                                       &lt;element name="yPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="GPSData" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="gpsVersionID" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLatitudeRefType" minOccurs="0"/&gt;
     *                             &lt;element name="GPSLatitude" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="gpsLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLongitudeRefType" minOccurs="0"/&gt;
     *                             &lt;element name="GPSLongitude" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="gpsAltitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsAltitudeRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsAltitude" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsTimeStamp" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsSatellites" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsStatus" type="{http://www.loc.gov/mix/v20}typeOfgpsStatusType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsMeasureMode" type="{http://www.loc.gov/mix/v20}typeOfgpsMeasureModeType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDOP" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsSpeedRef" type="{http://www.loc.gov/mix/v20}typeOfgpsSpeedRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsSpeed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsTrackRef" type="{http://www.loc.gov/mix/v20}typeOfgpsTrackRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsTrack" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsImgDirectionRef" type="{http://www.loc.gov/mix/v20}typeOfgpsImgDirectionRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsImgDirection" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsMapDatum" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDestLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLatitudeRefType" minOccurs="0"/&gt;
     *                             &lt;element name="GPSDestLatitude" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="gpsDestLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLongitudeRefType" minOccurs="0"/&gt;
     *                             &lt;element name="GPSDestLongitude" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="gpsDestBearingRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestBearingRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDestBearing" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDestDistanceRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestDistanceRefType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDestDistance" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsProcessingMethod" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsAreaInformation" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDateStamp" type="{http://www.loc.gov/mix/v20}dateType" minOccurs="0"/&gt;
     *                             &lt;element name="gpsDifferential" type="{http://www.loc.gov/mix/v20}typeOfgpsDifferentialType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "digitalCameraManufacturer",
        "digitalCameraModel",
        "cameraSensor",
        "cameraCaptureSettings"
    })
    public static class DigitalCameraCapture
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType digitalCameraManufacturer;
        @XmlElement(name = "DigitalCameraModel")
        protected ImageCaptureMetadataType.DigitalCameraCapture.DigitalCameraModel digitalCameraModel;
        protected TypeOfCameraSensorType cameraSensor;
        @XmlElement(name = "CameraCaptureSettings")
        protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings cameraCaptureSettings;

        /**
         * Recupera il valore della proprietà digitalCameraManufacturer.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getDigitalCameraManufacturer() {
            return digitalCameraManufacturer;
        }

        /**
         * Imposta il valore della proprietà digitalCameraManufacturer.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setDigitalCameraManufacturer(StringType value) {
            this.digitalCameraManufacturer = value;
        }

        /**
         * Recupera il valore della proprietà digitalCameraModel.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.DigitalCameraCapture.DigitalCameraModel }
         *     
         */
        public ImageCaptureMetadataType.DigitalCameraCapture.DigitalCameraModel getDigitalCameraModel() {
            return digitalCameraModel;
        }

        /**
         * Imposta il valore della proprietà digitalCameraModel.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.DigitalCameraCapture.DigitalCameraModel }
         *     
         */
        public void setDigitalCameraModel(ImageCaptureMetadataType.DigitalCameraCapture.DigitalCameraModel value) {
            this.digitalCameraModel = value;
        }

        /**
         * Recupera il valore della proprietà cameraSensor.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfCameraSensorType }
         *     
         */
        public TypeOfCameraSensorType getCameraSensor() {
            return cameraSensor;
        }

        /**
         * Imposta il valore della proprietà cameraSensor.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfCameraSensorType }
         *     
         */
        public void setCameraSensor(TypeOfCameraSensorType value) {
            this.cameraSensor = value;
        }

        /**
         * Recupera il valore della proprietà cameraCaptureSettings.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings }
         *     
         */
        public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings getCameraCaptureSettings() {
            return cameraCaptureSettings;
        }

        /**
         * Imposta il valore della proprietà cameraCaptureSettings.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings }
         *     
         */
        public void setCameraCaptureSettings(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings value) {
            this.cameraCaptureSettings = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="ImageData" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="fNumber" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                   &lt;element name="exposureTime" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                   &lt;element name="exposureProgram" type="{http://www.loc.gov/mix/v20}typeOfExposureProgramType" minOccurs="0"/&gt;
         *                   &lt;element name="spectralSensitivity" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                   &lt;element name="isoSpeedRatings" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *                   &lt;element name="oECF" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="exifVersion" type="{http://www.loc.gov/mix/v20}typeOfExifVersionType" minOccurs="0"/&gt;
         *                   &lt;element name="shutterSpeedValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="apertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="brightnessValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="exposureBiasValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="maxApertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="SubjectDistance" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="distance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
         *                             &lt;element name="MinMaxDistance" minOccurs="0"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
         *                                       &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="meteringMode" type="{http://www.loc.gov/mix/v20}typeOfMeteringModeType" minOccurs="0"/&gt;
         *                   &lt;element name="lightSource" type="{http://www.loc.gov/mix/v20}typeOfLightSourceType" minOccurs="0"/&gt;
         *                   &lt;element name="flash" type="{http://www.loc.gov/mix/v20}typeOfFlashType" minOccurs="0"/&gt;
         *                   &lt;element name="focalLength" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
         *                   &lt;element name="flashEnergy" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="backLight" type="{http://www.loc.gov/mix/v20}typeOfBackLightType" minOccurs="0"/&gt;
         *                   &lt;element name="exposureIndex" type="{http://www.loc.gov/mix/v20}typeOfPositiveRealType" minOccurs="0"/&gt;
         *                   &lt;element name="sensingMethod" type="{http://www.loc.gov/mix/v20}typeOfSensingMethodType" minOccurs="0"/&gt;
         *                   &lt;element name="cfaPattern" type="{http://www.loc.gov/mix/v20}integerType" minOccurs="0"/&gt;
         *                   &lt;element name="autoFocus" type="{http://www.loc.gov/mix/v20}typeOfAutoFocusType" minOccurs="0"/&gt;
         *                   &lt;element name="PrintAspectRatio" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="xPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                             &lt;element name="yPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="GPSData" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="gpsVersionID" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLatitudeRefType" minOccurs="0"/&gt;
         *                   &lt;element name="GPSLatitude" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="gpsLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLongitudeRefType" minOccurs="0"/&gt;
         *                   &lt;element name="GPSLongitude" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="gpsAltitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsAltitudeRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsAltitude" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsTimeStamp" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsSatellites" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsStatus" type="{http://www.loc.gov/mix/v20}typeOfgpsStatusType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsMeasureMode" type="{http://www.loc.gov/mix/v20}typeOfgpsMeasureModeType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDOP" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsSpeedRef" type="{http://www.loc.gov/mix/v20}typeOfgpsSpeedRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsSpeed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsTrackRef" type="{http://www.loc.gov/mix/v20}typeOfgpsTrackRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsTrack" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsImgDirectionRef" type="{http://www.loc.gov/mix/v20}typeOfgpsImgDirectionRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsImgDirection" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsMapDatum" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDestLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLatitudeRefType" minOccurs="0"/&gt;
         *                   &lt;element name="GPSDestLatitude" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="gpsDestLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLongitudeRefType" minOccurs="0"/&gt;
         *                   &lt;element name="GPSDestLongitude" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="gpsDestBearingRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestBearingRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDestBearing" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDestDistanceRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestDistanceRefType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDestDistance" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsProcessingMethod" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsAreaInformation" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDateStamp" type="{http://www.loc.gov/mix/v20}dateType" minOccurs="0"/&gt;
         *                   &lt;element name="gpsDifferential" type="{http://www.loc.gov/mix/v20}typeOfgpsDifferentialType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "imageData",
            "gpsData"
        })
        public static class CameraCaptureSettings
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ImageData")
            protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData imageData;
            @XmlElement(name = "GPSData")
            protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData gpsData;

            /**
             * Recupera il valore della proprietà imageData.
             * 
             * @return
             *     possible object is
             *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData }
             *     
             */
            public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData getImageData() {
                return imageData;
            }

            /**
             * Imposta il valore della proprietà imageData.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData }
             *     
             */
            public void setImageData(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData value) {
                this.imageData = value;
            }

            /**
             * Recupera il valore della proprietà gpsData.
             * 
             * @return
             *     possible object is
             *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData }
             *     
             */
            public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData getGPSData() {
                return gpsData;
            }

            /**
             * Imposta il valore della proprietà gpsData.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData }
             *     
             */
            public void setGPSData(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData value) {
                this.gpsData = value;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="gpsVersionID" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLatitudeRefType" minOccurs="0"/&gt;
             *         &lt;element name="GPSLatitude" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="gpsLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsLongitudeRefType" minOccurs="0"/&gt;
             *         &lt;element name="GPSLongitude" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="gpsAltitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsAltitudeRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsAltitude" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsTimeStamp" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsSatellites" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsStatus" type="{http://www.loc.gov/mix/v20}typeOfgpsStatusType" minOccurs="0"/&gt;
             *         &lt;element name="gpsMeasureMode" type="{http://www.loc.gov/mix/v20}typeOfgpsMeasureModeType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDOP" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsSpeedRef" type="{http://www.loc.gov/mix/v20}typeOfgpsSpeedRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsSpeed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsTrackRef" type="{http://www.loc.gov/mix/v20}typeOfgpsTrackRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsTrack" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsImgDirectionRef" type="{http://www.loc.gov/mix/v20}typeOfgpsImgDirectionRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsImgDirection" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsMapDatum" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDestLatitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLatitudeRefType" minOccurs="0"/&gt;
             *         &lt;element name="GPSDestLatitude" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="gpsDestLongitudeRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestLongitudeRefType" minOccurs="0"/&gt;
             *         &lt;element name="GPSDestLongitude" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="gpsDestBearingRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestBearingRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDestBearing" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDestDistanceRef" type="{http://www.loc.gov/mix/v20}typeOfgpsDestDistanceRefType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDestDistance" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="gpsProcessingMethod" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsAreaInformation" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDateStamp" type="{http://www.loc.gov/mix/v20}dateType" minOccurs="0"/&gt;
             *         &lt;element name="gpsDifferential" type="{http://www.loc.gov/mix/v20}typeOfgpsDifferentialType" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "gpsVersionID",
                "gpsLatitudeRef",
                "gpsLatitude",
                "gpsLongitudeRef",
                "gpsLongitude",
                "gpsAltitudeRef",
                "gpsAltitude",
                "gpsTimeStamp",
                "gpsSatellites",
                "gpsStatus",
                "gpsMeasureMode",
                "gpsDOP",
                "gpsSpeedRef",
                "gpsSpeed",
                "gpsTrackRef",
                "gpsTrack",
                "gpsImgDirectionRef",
                "gpsImgDirection",
                "gpsMapDatum",
                "gpsDestLatitudeRef",
                "gpsDestLatitude",
                "gpsDestLongitudeRef",
                "gpsDestLongitude",
                "gpsDestBearingRef",
                "gpsDestBearing",
                "gpsDestDistanceRef",
                "gpsDestDistance",
                "gpsProcessingMethod",
                "gpsAreaInformation",
                "gpsDateStamp",
                "gpsDifferential"
            })
            public static class GPSData
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected StringType gpsVersionID;
                protected TypeOfgpsLatitudeRefType gpsLatitudeRef;
                @XmlElement(name = "GPSLatitude")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLatitude gpsLatitude;
                protected TypeOfgpsLongitudeRefType gpsLongitudeRef;
                @XmlElement(name = "GPSLongitude")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLongitude gpsLongitude;
                protected TypeOfgpsAltitudeRefType gpsAltitudeRef;
                protected RationalType gpsAltitude;
                protected StringType gpsTimeStamp;
                protected StringType gpsSatellites;
                protected TypeOfgpsStatusType gpsStatus;
                protected TypeOfgpsMeasureModeType gpsMeasureMode;
                protected RationalType gpsDOP;
                protected TypeOfgpsSpeedRefType gpsSpeedRef;
                protected RationalType gpsSpeed;
                protected TypeOfgpsTrackRefType gpsTrackRef;
                protected RationalType gpsTrack;
                protected TypeOfgpsImgDirectionRefType gpsImgDirectionRef;
                protected RationalType gpsImgDirection;
                protected StringType gpsMapDatum;
                protected TypeOfgpsDestLatitudeRefType gpsDestLatitudeRef;
                @XmlElement(name = "GPSDestLatitude")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLatitude gpsDestLatitude;
                protected TypeOfgpsDestLongitudeRefType gpsDestLongitudeRef;
                @XmlElement(name = "GPSDestLongitude")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLongitude gpsDestLongitude;
                protected TypeOfgpsDestBearingRefType gpsDestBearingRef;
                protected RationalType gpsDestBearing;
                protected TypeOfgpsDestDistanceRefType gpsDestDistanceRef;
                protected RationalType gpsDestDistance;
                protected StringType gpsProcessingMethod;
                protected StringType gpsAreaInformation;
                protected DateType gpsDateStamp;
                protected TypeOfgpsDifferentialType gpsDifferential;

                /**
                 * Recupera il valore della proprietà gpsVersionID.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsVersionID() {
                    return gpsVersionID;
                }

                /**
                 * Imposta il valore della proprietà gpsVersionID.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsVersionID(StringType value) {
                    this.gpsVersionID = value;
                }

                /**
                 * Recupera il valore della proprietà gpsLatitudeRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsLatitudeRefType }
                 *     
                 */
                public TypeOfgpsLatitudeRefType getGpsLatitudeRef() {
                    return gpsLatitudeRef;
                }

                /**
                 * Imposta il valore della proprietà gpsLatitudeRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsLatitudeRefType }
                 *     
                 */
                public void setGpsLatitudeRef(TypeOfgpsLatitudeRefType value) {
                    this.gpsLatitudeRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsLatitude.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLatitude }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLatitude getGPSLatitude() {
                    return gpsLatitude;
                }

                /**
                 * Imposta il valore della proprietà gpsLatitude.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLatitude }
                 *     
                 */
                public void setGPSLatitude(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLatitude value) {
                    this.gpsLatitude = value;
                }

                /**
                 * Recupera il valore della proprietà gpsLongitudeRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsLongitudeRefType }
                 *     
                 */
                public TypeOfgpsLongitudeRefType getGpsLongitudeRef() {
                    return gpsLongitudeRef;
                }

                /**
                 * Imposta il valore della proprietà gpsLongitudeRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsLongitudeRefType }
                 *     
                 */
                public void setGpsLongitudeRef(TypeOfgpsLongitudeRefType value) {
                    this.gpsLongitudeRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsLongitude.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLongitude }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLongitude getGPSLongitude() {
                    return gpsLongitude;
                }

                /**
                 * Imposta il valore della proprietà gpsLongitude.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLongitude }
                 *     
                 */
                public void setGPSLongitude(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSLongitude value) {
                    this.gpsLongitude = value;
                }

                /**
                 * Recupera il valore della proprietà gpsAltitudeRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsAltitudeRefType }
                 *     
                 */
                public TypeOfgpsAltitudeRefType getGpsAltitudeRef() {
                    return gpsAltitudeRef;
                }

                /**
                 * Imposta il valore della proprietà gpsAltitudeRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsAltitudeRefType }
                 *     
                 */
                public void setGpsAltitudeRef(TypeOfgpsAltitudeRefType value) {
                    this.gpsAltitudeRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsAltitude.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsAltitude() {
                    return gpsAltitude;
                }

                /**
                 * Imposta il valore della proprietà gpsAltitude.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsAltitude(RationalType value) {
                    this.gpsAltitude = value;
                }

                /**
                 * Recupera il valore della proprietà gpsTimeStamp.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsTimeStamp() {
                    return gpsTimeStamp;
                }

                /**
                 * Imposta il valore della proprietà gpsTimeStamp.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsTimeStamp(StringType value) {
                    this.gpsTimeStamp = value;
                }

                /**
                 * Recupera il valore della proprietà gpsSatellites.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsSatellites() {
                    return gpsSatellites;
                }

                /**
                 * Imposta il valore della proprietà gpsSatellites.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsSatellites(StringType value) {
                    this.gpsSatellites = value;
                }

                /**
                 * Recupera il valore della proprietà gpsStatus.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsStatusType }
                 *     
                 */
                public TypeOfgpsStatusType getGpsStatus() {
                    return gpsStatus;
                }

                /**
                 * Imposta il valore della proprietà gpsStatus.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsStatusType }
                 *     
                 */
                public void setGpsStatus(TypeOfgpsStatusType value) {
                    this.gpsStatus = value;
                }

                /**
                 * Recupera il valore della proprietà gpsMeasureMode.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsMeasureModeType }
                 *     
                 */
                public TypeOfgpsMeasureModeType getGpsMeasureMode() {
                    return gpsMeasureMode;
                }

                /**
                 * Imposta il valore della proprietà gpsMeasureMode.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsMeasureModeType }
                 *     
                 */
                public void setGpsMeasureMode(TypeOfgpsMeasureModeType value) {
                    this.gpsMeasureMode = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDOP.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsDOP() {
                    return gpsDOP;
                }

                /**
                 * Imposta il valore della proprietà gpsDOP.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsDOP(RationalType value) {
                    this.gpsDOP = value;
                }

                /**
                 * Recupera il valore della proprietà gpsSpeedRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsSpeedRefType }
                 *     
                 */
                public TypeOfgpsSpeedRefType getGpsSpeedRef() {
                    return gpsSpeedRef;
                }

                /**
                 * Imposta il valore della proprietà gpsSpeedRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsSpeedRefType }
                 *     
                 */
                public void setGpsSpeedRef(TypeOfgpsSpeedRefType value) {
                    this.gpsSpeedRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsSpeed.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsSpeed() {
                    return gpsSpeed;
                }

                /**
                 * Imposta il valore della proprietà gpsSpeed.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsSpeed(RationalType value) {
                    this.gpsSpeed = value;
                }

                /**
                 * Recupera il valore della proprietà gpsTrackRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsTrackRefType }
                 *     
                 */
                public TypeOfgpsTrackRefType getGpsTrackRef() {
                    return gpsTrackRef;
                }

                /**
                 * Imposta il valore della proprietà gpsTrackRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsTrackRefType }
                 *     
                 */
                public void setGpsTrackRef(TypeOfgpsTrackRefType value) {
                    this.gpsTrackRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsTrack.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsTrack() {
                    return gpsTrack;
                }

                /**
                 * Imposta il valore della proprietà gpsTrack.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsTrack(RationalType value) {
                    this.gpsTrack = value;
                }

                /**
                 * Recupera il valore della proprietà gpsImgDirectionRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsImgDirectionRefType }
                 *     
                 */
                public TypeOfgpsImgDirectionRefType getGpsImgDirectionRef() {
                    return gpsImgDirectionRef;
                }

                /**
                 * Imposta il valore della proprietà gpsImgDirectionRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsImgDirectionRefType }
                 *     
                 */
                public void setGpsImgDirectionRef(TypeOfgpsImgDirectionRefType value) {
                    this.gpsImgDirectionRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsImgDirection.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsImgDirection() {
                    return gpsImgDirection;
                }

                /**
                 * Imposta il valore della proprietà gpsImgDirection.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsImgDirection(RationalType value) {
                    this.gpsImgDirection = value;
                }

                /**
                 * Recupera il valore della proprietà gpsMapDatum.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsMapDatum() {
                    return gpsMapDatum;
                }

                /**
                 * Imposta il valore della proprietà gpsMapDatum.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsMapDatum(StringType value) {
                    this.gpsMapDatum = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestLatitudeRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsDestLatitudeRefType }
                 *     
                 */
                public TypeOfgpsDestLatitudeRefType getGpsDestLatitudeRef() {
                    return gpsDestLatitudeRef;
                }

                /**
                 * Imposta il valore della proprietà gpsDestLatitudeRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsDestLatitudeRefType }
                 *     
                 */
                public void setGpsDestLatitudeRef(TypeOfgpsDestLatitudeRefType value) {
                    this.gpsDestLatitudeRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestLatitude.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLatitude }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLatitude getGPSDestLatitude() {
                    return gpsDestLatitude;
                }

                /**
                 * Imposta il valore della proprietà gpsDestLatitude.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLatitude }
                 *     
                 */
                public void setGPSDestLatitude(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLatitude value) {
                    this.gpsDestLatitude = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestLongitudeRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsDestLongitudeRefType }
                 *     
                 */
                public TypeOfgpsDestLongitudeRefType getGpsDestLongitudeRef() {
                    return gpsDestLongitudeRef;
                }

                /**
                 * Imposta il valore della proprietà gpsDestLongitudeRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsDestLongitudeRefType }
                 *     
                 */
                public void setGpsDestLongitudeRef(TypeOfgpsDestLongitudeRefType value) {
                    this.gpsDestLongitudeRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestLongitude.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLongitude }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLongitude getGPSDestLongitude() {
                    return gpsDestLongitude;
                }

                /**
                 * Imposta il valore della proprietà gpsDestLongitude.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLongitude }
                 *     
                 */
                public void setGPSDestLongitude(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.GPSData.GPSDestLongitude value) {
                    this.gpsDestLongitude = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestBearingRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsDestBearingRefType }
                 *     
                 */
                public TypeOfgpsDestBearingRefType getGpsDestBearingRef() {
                    return gpsDestBearingRef;
                }

                /**
                 * Imposta il valore della proprietà gpsDestBearingRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsDestBearingRefType }
                 *     
                 */
                public void setGpsDestBearingRef(TypeOfgpsDestBearingRefType value) {
                    this.gpsDestBearingRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestBearing.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsDestBearing() {
                    return gpsDestBearing;
                }

                /**
                 * Imposta il valore della proprietà gpsDestBearing.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsDestBearing(RationalType value) {
                    this.gpsDestBearing = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestDistanceRef.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsDestDistanceRefType }
                 *     
                 */
                public TypeOfgpsDestDistanceRefType getGpsDestDistanceRef() {
                    return gpsDestDistanceRef;
                }

                /**
                 * Imposta il valore della proprietà gpsDestDistanceRef.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsDestDistanceRefType }
                 *     
                 */
                public void setGpsDestDistanceRef(TypeOfgpsDestDistanceRefType value) {
                    this.gpsDestDistanceRef = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDestDistance.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getGpsDestDistance() {
                    return gpsDestDistance;
                }

                /**
                 * Imposta il valore della proprietà gpsDestDistance.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setGpsDestDistance(RationalType value) {
                    this.gpsDestDistance = value;
                }

                /**
                 * Recupera il valore della proprietà gpsProcessingMethod.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsProcessingMethod() {
                    return gpsProcessingMethod;
                }

                /**
                 * Imposta il valore della proprietà gpsProcessingMethod.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsProcessingMethod(StringType value) {
                    this.gpsProcessingMethod = value;
                }

                /**
                 * Recupera il valore della proprietà gpsAreaInformation.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getGpsAreaInformation() {
                    return gpsAreaInformation;
                }

                /**
                 * Imposta il valore della proprietà gpsAreaInformation.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setGpsAreaInformation(StringType value) {
                    this.gpsAreaInformation = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDateStamp.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DateType }
                 *     
                 */
                public DateType getGpsDateStamp() {
                    return gpsDateStamp;
                }

                /**
                 * Imposta il valore della proprietà gpsDateStamp.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DateType }
                 *     
                 */
                public void setGpsDateStamp(DateType value) {
                    this.gpsDateStamp = value;
                }

                /**
                 * Recupera il valore della proprietà gpsDifferential.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfgpsDifferentialType }
                 *     
                 */
                public TypeOfgpsDifferentialType getGpsDifferential() {
                    return gpsDifferential;
                }

                /**
                 * Imposta il valore della proprietà gpsDifferential.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfgpsDifferentialType }
                 *     
                 */
                public void setGpsDifferential(TypeOfgpsDifferentialType value) {
                    this.gpsDifferential = value;
                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "degrees",
                    "minutes",
                    "seconds"
                })
                public static class GPSDestLatitude
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected RationalType degrees;
                    protected RationalType minutes;
                    protected RationalType seconds;

                    /**
                     * Recupera il valore della proprietà degrees.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getDegrees() {
                        return degrees;
                    }

                    /**
                     * Imposta il valore della proprietà degrees.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setDegrees(RationalType value) {
                        this.degrees = value;
                    }

                    /**
                     * Recupera il valore della proprietà minutes.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getMinutes() {
                        return minutes;
                    }

                    /**
                     * Imposta il valore della proprietà minutes.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setMinutes(RationalType value) {
                        this.minutes = value;
                    }

                    /**
                     * Recupera il valore della proprietà seconds.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getSeconds() {
                        return seconds;
                    }

                    /**
                     * Imposta il valore della proprietà seconds.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setSeconds(RationalType value) {
                        this.seconds = value;
                    }

                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "degrees",
                    "minutes",
                    "seconds"
                })
                public static class GPSDestLongitude
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected RationalType degrees;
                    protected RationalType minutes;
                    protected RationalType seconds;

                    /**
                     * Recupera il valore della proprietà degrees.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getDegrees() {
                        return degrees;
                    }

                    /**
                     * Imposta il valore della proprietà degrees.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setDegrees(RationalType value) {
                        this.degrees = value;
                    }

                    /**
                     * Recupera il valore della proprietà minutes.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getMinutes() {
                        return minutes;
                    }

                    /**
                     * Imposta il valore della proprietà minutes.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setMinutes(RationalType value) {
                        this.minutes = value;
                    }

                    /**
                     * Recupera il valore della proprietà seconds.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getSeconds() {
                        return seconds;
                    }

                    /**
                     * Imposta il valore della proprietà seconds.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setSeconds(RationalType value) {
                        this.seconds = value;
                    }

                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "degrees",
                    "minutes",
                    "seconds"
                })
                public static class GPSLatitude
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected RationalType degrees;
                    protected RationalType minutes;
                    protected RationalType seconds;

                    /**
                     * Recupera il valore della proprietà degrees.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getDegrees() {
                        return degrees;
                    }

                    /**
                     * Imposta il valore della proprietà degrees.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setDegrees(RationalType value) {
                        this.degrees = value;
                    }

                    /**
                     * Recupera il valore della proprietà minutes.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getMinutes() {
                        return minutes;
                    }

                    /**
                     * Imposta il valore della proprietà minutes.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setMinutes(RationalType value) {
                        this.minutes = value;
                    }

                    /**
                     * Recupera il valore della proprietà seconds.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getSeconds() {
                        return seconds;
                    }

                    /**
                     * Imposta il valore della proprietà seconds.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setSeconds(RationalType value) {
                        this.seconds = value;
                    }

                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;group ref="{http://www.loc.gov/mix/v20}gpsGroup"/&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "degrees",
                    "minutes",
                    "seconds"
                })
                public static class GPSLongitude
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected RationalType degrees;
                    protected RationalType minutes;
                    protected RationalType seconds;

                    /**
                     * Recupera il valore della proprietà degrees.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getDegrees() {
                        return degrees;
                    }

                    /**
                     * Imposta il valore della proprietà degrees.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setDegrees(RationalType value) {
                        this.degrees = value;
                    }

                    /**
                     * Recupera il valore della proprietà minutes.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getMinutes() {
                        return minutes;
                    }

                    /**
                     * Imposta il valore della proprietà minutes.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setMinutes(RationalType value) {
                        this.minutes = value;
                    }

                    /**
                     * Recupera il valore della proprietà seconds.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getSeconds() {
                        return seconds;
                    }

                    /**
                     * Imposta il valore della proprietà seconds.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setSeconds(RationalType value) {
                        this.seconds = value;
                    }

                }

            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="fNumber" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *         &lt;element name="exposureTime" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *         &lt;element name="exposureProgram" type="{http://www.loc.gov/mix/v20}typeOfExposureProgramType" minOccurs="0"/&gt;
             *         &lt;element name="spectralSensitivity" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
             *         &lt;element name="isoSpeedRatings" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
             *         &lt;element name="oECF" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="exifVersion" type="{http://www.loc.gov/mix/v20}typeOfExifVersionType" minOccurs="0"/&gt;
             *         &lt;element name="shutterSpeedValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="apertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="brightnessValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="exposureBiasValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="maxApertureValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="SubjectDistance" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="distance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
             *                   &lt;element name="MinMaxDistance" minOccurs="0"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
             *                             &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="meteringMode" type="{http://www.loc.gov/mix/v20}typeOfMeteringModeType" minOccurs="0"/&gt;
             *         &lt;element name="lightSource" type="{http://www.loc.gov/mix/v20}typeOfLightSourceType" minOccurs="0"/&gt;
             *         &lt;element name="flash" type="{http://www.loc.gov/mix/v20}typeOfFlashType" minOccurs="0"/&gt;
             *         &lt;element name="focalLength" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
             *         &lt;element name="flashEnergy" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *         &lt;element name="backLight" type="{http://www.loc.gov/mix/v20}typeOfBackLightType" minOccurs="0"/&gt;
             *         &lt;element name="exposureIndex" type="{http://www.loc.gov/mix/v20}typeOfPositiveRealType" minOccurs="0"/&gt;
             *         &lt;element name="sensingMethod" type="{http://www.loc.gov/mix/v20}typeOfSensingMethodType" minOccurs="0"/&gt;
             *         &lt;element name="cfaPattern" type="{http://www.loc.gov/mix/v20}integerType" minOccurs="0"/&gt;
             *         &lt;element name="autoFocus" type="{http://www.loc.gov/mix/v20}typeOfAutoFocusType" minOccurs="0"/&gt;
             *         &lt;element name="PrintAspectRatio" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="xPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *                   &lt;element name="yPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "fNumber",
                "exposureTime",
                "exposureProgram",
                "spectralSensitivities",
                "isoSpeedRatings",
                "oecf",
                "exifVersion",
                "shutterSpeedValue",
                "apertureValue",
                "brightnessValue",
                "exposureBiasValue",
                "maxApertureValue",
                "subjectDistance",
                "meteringMode",
                "lightSource",
                "flash",
                "focalLength",
                "flashEnergy",
                "backLight",
                "exposureIndex",
                "sensingMethod",
                "cfaPattern",
                "autoFocus",
                "printAspectRatio"
            })
            public static class ImageData
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected TypeOfNonNegativeRealType fNumber;
                protected TypeOfNonNegativeRealType exposureTime;
                protected TypeOfExposureProgramType exposureProgram;
                @XmlElement(name = "spectralSensitivity")
                protected List<StringType> spectralSensitivities;
                protected PositiveIntegerType isoSpeedRatings;
                @XmlElement(name = "oECF")
                protected RationalType oecf;
                protected TypeOfExifVersionType exifVersion;
                protected RationalType shutterSpeedValue;
                protected RationalType apertureValue;
                protected RationalType brightnessValue;
                protected RationalType exposureBiasValue;
                protected RationalType maxApertureValue;
                @XmlElement(name = "SubjectDistance")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance subjectDistance;
                protected TypeOfMeteringModeType meteringMode;
                protected TypeOfLightSourceType lightSource;
                protected TypeOfFlashType flash;
                protected TypeOfNonNegativeDecimalType focalLength;
                protected RationalType flashEnergy;
                protected TypeOfBackLightType backLight;
                protected TypeOfPositiveRealType exposureIndex;
                protected TypeOfSensingMethodType sensingMethod;
                protected IntegerType cfaPattern;
                protected TypeOfAutoFocusType autoFocus;
                @XmlElement(name = "PrintAspectRatio")
                protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.PrintAspectRatio printAspectRatio;

                /**
                 * Recupera il valore della proprietà fNumber.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public TypeOfNonNegativeRealType getFNumber() {
                    return fNumber;
                }

                /**
                 * Imposta il valore della proprietà fNumber.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public void setFNumber(TypeOfNonNegativeRealType value) {
                    this.fNumber = value;
                }

                /**
                 * Recupera il valore della proprietà exposureTime.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public TypeOfNonNegativeRealType getExposureTime() {
                    return exposureTime;
                }

                /**
                 * Imposta il valore della proprietà exposureTime.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public void setExposureTime(TypeOfNonNegativeRealType value) {
                    this.exposureTime = value;
                }

                /**
                 * Recupera il valore della proprietà exposureProgram.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfExposureProgramType }
                 *     
                 */
                public TypeOfExposureProgramType getExposureProgram() {
                    return exposureProgram;
                }

                /**
                 * Imposta il valore della proprietà exposureProgram.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfExposureProgramType }
                 *     
                 */
                public void setExposureProgram(TypeOfExposureProgramType value) {
                    this.exposureProgram = value;
                }

                /**
                 * Gets the value of the spectralSensitivities property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the spectralSensitivities property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSpectralSensitivities().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link StringType }
                 * 
                 * 
                 */
                public List<StringType> getSpectralSensitivities() {
                    if (spectralSensitivities == null) {
                        spectralSensitivities = new ArrayList<StringType>();
                    }
                    return this.spectralSensitivities;
                }

                /**
                 * Recupera il valore della proprietà isoSpeedRatings.
                 * 
                 * @return
                 *     possible object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public PositiveIntegerType getIsoSpeedRatings() {
                    return isoSpeedRatings;
                }

                /**
                 * Imposta il valore della proprietà isoSpeedRatings.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public void setIsoSpeedRatings(PositiveIntegerType value) {
                    this.isoSpeedRatings = value;
                }

                /**
                 * Recupera il valore della proprietà oecf.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getOECF() {
                    return oecf;
                }

                /**
                 * Imposta il valore della proprietà oecf.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setOECF(RationalType value) {
                    this.oecf = value;
                }

                /**
                 * Recupera il valore della proprietà exifVersion.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfExifVersionType }
                 *     
                 */
                public TypeOfExifVersionType getExifVersion() {
                    return exifVersion;
                }

                /**
                 * Imposta il valore della proprietà exifVersion.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfExifVersionType }
                 *     
                 */
                public void setExifVersion(TypeOfExifVersionType value) {
                    this.exifVersion = value;
                }

                /**
                 * Recupera il valore della proprietà shutterSpeedValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getShutterSpeedValue() {
                    return shutterSpeedValue;
                }

                /**
                 * Imposta il valore della proprietà shutterSpeedValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setShutterSpeedValue(RationalType value) {
                    this.shutterSpeedValue = value;
                }

                /**
                 * Recupera il valore della proprietà apertureValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getApertureValue() {
                    return apertureValue;
                }

                /**
                 * Imposta il valore della proprietà apertureValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setApertureValue(RationalType value) {
                    this.apertureValue = value;
                }

                /**
                 * Recupera il valore della proprietà brightnessValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getBrightnessValue() {
                    return brightnessValue;
                }

                /**
                 * Imposta il valore della proprietà brightnessValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setBrightnessValue(RationalType value) {
                    this.brightnessValue = value;
                }

                /**
                 * Recupera il valore della proprietà exposureBiasValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getExposureBiasValue() {
                    return exposureBiasValue;
                }

                /**
                 * Imposta il valore della proprietà exposureBiasValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setExposureBiasValue(RationalType value) {
                    this.exposureBiasValue = value;
                }

                /**
                 * Recupera il valore della proprietà maxApertureValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getMaxApertureValue() {
                    return maxApertureValue;
                }

                /**
                 * Imposta il valore della proprietà maxApertureValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setMaxApertureValue(RationalType value) {
                    this.maxApertureValue = value;
                }

                /**
                 * Recupera il valore della proprietà subjectDistance.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance getSubjectDistance() {
                    return subjectDistance;
                }

                /**
                 * Imposta il valore della proprietà subjectDistance.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance }
                 *     
                 */
                public void setSubjectDistance(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance value) {
                    this.subjectDistance = value;
                }

                /**
                 * Recupera il valore della proprietà meteringMode.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfMeteringModeType }
                 *     
                 */
                public TypeOfMeteringModeType getMeteringMode() {
                    return meteringMode;
                }

                /**
                 * Imposta il valore della proprietà meteringMode.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfMeteringModeType }
                 *     
                 */
                public void setMeteringMode(TypeOfMeteringModeType value) {
                    this.meteringMode = value;
                }

                /**
                 * Recupera il valore della proprietà lightSource.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfLightSourceType }
                 *     
                 */
                public TypeOfLightSourceType getLightSource() {
                    return lightSource;
                }

                /**
                 * Imposta il valore della proprietà lightSource.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfLightSourceType }
                 *     
                 */
                public void setLightSource(TypeOfLightSourceType value) {
                    this.lightSource = value;
                }

                /**
                 * Recupera il valore della proprietà flash.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfFlashType }
                 *     
                 */
                public TypeOfFlashType getFlash() {
                    return flash;
                }

                /**
                 * Imposta il valore della proprietà flash.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfFlashType }
                 *     
                 */
                public void setFlash(TypeOfFlashType value) {
                    this.flash = value;
                }

                /**
                 * Recupera il valore della proprietà focalLength.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeDecimalType }
                 *     
                 */
                public TypeOfNonNegativeDecimalType getFocalLength() {
                    return focalLength;
                }

                /**
                 * Imposta il valore della proprietà focalLength.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeDecimalType }
                 *     
                 */
                public void setFocalLength(TypeOfNonNegativeDecimalType value) {
                    this.focalLength = value;
                }

                /**
                 * Recupera il valore della proprietà flashEnergy.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RationalType }
                 *     
                 */
                public RationalType getFlashEnergy() {
                    return flashEnergy;
                }

                /**
                 * Imposta il valore della proprietà flashEnergy.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RationalType }
                 *     
                 */
                public void setFlashEnergy(RationalType value) {
                    this.flashEnergy = value;
                }

                /**
                 * Recupera il valore della proprietà backLight.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfBackLightType }
                 *     
                 */
                public TypeOfBackLightType getBackLight() {
                    return backLight;
                }

                /**
                 * Imposta il valore della proprietà backLight.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfBackLightType }
                 *     
                 */
                public void setBackLight(TypeOfBackLightType value) {
                    this.backLight = value;
                }

                /**
                 * Recupera il valore della proprietà exposureIndex.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfPositiveRealType }
                 *     
                 */
                public TypeOfPositiveRealType getExposureIndex() {
                    return exposureIndex;
                }

                /**
                 * Imposta il valore della proprietà exposureIndex.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfPositiveRealType }
                 *     
                 */
                public void setExposureIndex(TypeOfPositiveRealType value) {
                    this.exposureIndex = value;
                }

                /**
                 * Recupera il valore della proprietà sensingMethod.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfSensingMethodType }
                 *     
                 */
                public TypeOfSensingMethodType getSensingMethod() {
                    return sensingMethod;
                }

                /**
                 * Imposta il valore della proprietà sensingMethod.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfSensingMethodType }
                 *     
                 */
                public void setSensingMethod(TypeOfSensingMethodType value) {
                    this.sensingMethod = value;
                }

                /**
                 * Recupera il valore della proprietà cfaPattern.
                 * 
                 * @return
                 *     possible object is
                 *     {@link IntegerType }
                 *     
                 */
                public IntegerType getCfaPattern() {
                    return cfaPattern;
                }

                /**
                 * Imposta il valore della proprietà cfaPattern.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link IntegerType }
                 *     
                 */
                public void setCfaPattern(IntegerType value) {
                    this.cfaPattern = value;
                }

                /**
                 * Recupera il valore della proprietà autoFocus.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfAutoFocusType }
                 *     
                 */
                public TypeOfAutoFocusType getAutoFocus() {
                    return autoFocus;
                }

                /**
                 * Imposta il valore della proprietà autoFocus.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfAutoFocusType }
                 *     
                 */
                public void setAutoFocus(TypeOfAutoFocusType value) {
                    this.autoFocus = value;
                }

                /**
                 * Recupera il valore della proprietà printAspectRatio.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.PrintAspectRatio }
                 *     
                 */
                public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.PrintAspectRatio getPrintAspectRatio() {
                    return printAspectRatio;
                }

                /**
                 * Imposta il valore della proprietà printAspectRatio.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.PrintAspectRatio }
                 *     
                 */
                public void setPrintAspectRatio(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.PrintAspectRatio value) {
                    this.printAspectRatio = value;
                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="xPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
                 *         &lt;element name="yPrintAspectRatio" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "xPrintAspectRatio",
                    "yPrintAspectRatio"
                })
                public static class PrintAspectRatio
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected TypeOfNonNegativeRealType xPrintAspectRatio;
                    protected TypeOfNonNegativeRealType yPrintAspectRatio;

                    /**
                     * Recupera il valore della proprietà xPrintAspectRatio.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfNonNegativeRealType }
                     *     
                     */
                    public TypeOfNonNegativeRealType getXPrintAspectRatio() {
                        return xPrintAspectRatio;
                    }

                    /**
                     * Imposta il valore della proprietà xPrintAspectRatio.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfNonNegativeRealType }
                     *     
                     */
                    public void setXPrintAspectRatio(TypeOfNonNegativeRealType value) {
                        this.xPrintAspectRatio = value;
                    }

                    /**
                     * Recupera il valore della proprietà yPrintAspectRatio.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfNonNegativeRealType }
                     *     
                     */
                    public TypeOfNonNegativeRealType getYPrintAspectRatio() {
                        return yPrintAspectRatio;
                    }

                    /**
                     * Imposta il valore della proprietà yPrintAspectRatio.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfNonNegativeRealType }
                     *     
                     */
                    public void setYPrintAspectRatio(TypeOfNonNegativeRealType value) {
                        this.yPrintAspectRatio = value;
                    }

                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="distance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
                 *         &lt;element name="MinMaxDistance" minOccurs="0"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
                 *                   &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "distance",
                    "minMaxDistance"
                })
                public static class SubjectDistance
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected TypeOfNonNegativeDecimalType distance;
                    @XmlElement(name = "MinMaxDistance")
                    protected ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance.MinMaxDistance minMaxDistance;

                    /**
                     * Recupera il valore della proprietà distance.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfNonNegativeDecimalType }
                     *     
                     */
                    public TypeOfNonNegativeDecimalType getDistance() {
                        return distance;
                    }

                    /**
                     * Imposta il valore della proprietà distance.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfNonNegativeDecimalType }
                     *     
                     */
                    public void setDistance(TypeOfNonNegativeDecimalType value) {
                        this.distance = value;
                    }

                    /**
                     * Recupera il valore della proprietà minMaxDistance.
                     * 
                     * @return
                     *     possible object is
                     *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance.MinMaxDistance }
                     *     
                     */
                    public ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance.MinMaxDistance getMinMaxDistance() {
                        return minMaxDistance;
                    }

                    /**
                     * Imposta il valore della proprietà minMaxDistance.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance.MinMaxDistance }
                     *     
                     */
                    public void setMinMaxDistance(ImageCaptureMetadataType.DigitalCameraCapture.CameraCaptureSettings.ImageData.SubjectDistance.MinMaxDistance value) {
                        this.minMaxDistance = value;
                    }


                    /**
                     * <p>Classe Java per anonymous complex type.
                     * 
                     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence&gt;
                     *         &lt;element name="minDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
                     *         &lt;element name="maxDistance" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeDecimalType" minOccurs="0"/&gt;
                     *       &lt;/sequence&gt;
                     *     &lt;/restriction&gt;
                     *   &lt;/complexContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "minDistance",
                        "maxDistance"
                    })
                    public static class MinMaxDistance
                        implements Serializable
                    {

                        private final static long serialVersionUID = -1L;
                        protected TypeOfNonNegativeDecimalType minDistance;
                        protected TypeOfNonNegativeDecimalType maxDistance;

                        /**
                         * Recupera il valore della proprietà minDistance.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TypeOfNonNegativeDecimalType }
                         *     
                         */
                        public TypeOfNonNegativeDecimalType getMinDistance() {
                            return minDistance;
                        }

                        /**
                         * Imposta il valore della proprietà minDistance.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TypeOfNonNegativeDecimalType }
                         *     
                         */
                        public void setMinDistance(TypeOfNonNegativeDecimalType value) {
                            this.minDistance = value;
                        }

                        /**
                         * Recupera il valore della proprietà maxDistance.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TypeOfNonNegativeDecimalType }
                         *     
                         */
                        public TypeOfNonNegativeDecimalType getMaxDistance() {
                            return maxDistance;
                        }

                        /**
                         * Imposta il valore della proprietà maxDistance.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TypeOfNonNegativeDecimalType }
                         *     
                         */
                        public void setMaxDistance(TypeOfNonNegativeDecimalType value) {
                            this.maxDistance = value;
                        }

                    }

                }

            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="digitalCameraModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="digitalCameraModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="digitalCameraModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "digitalCameraModelName",
            "digitalCameraModelNumber",
            "digitalCameraModelSerialNo"
        })
        public static class DigitalCameraModel
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType digitalCameraModelName;
            protected StringType digitalCameraModelNumber;
            protected StringType digitalCameraModelSerialNo;

            /**
             * Recupera il valore della proprietà digitalCameraModelName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getDigitalCameraModelName() {
                return digitalCameraModelName;
            }

            /**
             * Imposta il valore della proprietà digitalCameraModelName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setDigitalCameraModelName(StringType value) {
                this.digitalCameraModelName = value;
            }

            /**
             * Recupera il valore della proprietà digitalCameraModelNumber.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getDigitalCameraModelNumber() {
                return digitalCameraModelNumber;
            }

            /**
             * Imposta il valore della proprietà digitalCameraModelNumber.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setDigitalCameraModelNumber(StringType value) {
                this.digitalCameraModelNumber = value;
            }

            /**
             * Recupera il valore della proprietà digitalCameraModelSerialNo.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getDigitalCameraModelSerialNo() {
                return digitalCameraModelSerialNo;
            }

            /**
             * Imposta il valore della proprietà digitalCameraModelSerialNo.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setDigitalCameraModelSerialNo(StringType value) {
                this.digitalCameraModelSerialNo = value;
            }

        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="dateTimeCreated" type="{http://www.loc.gov/mix/v20}typeOfDateType" minOccurs="0"/&gt;
     *         &lt;element name="imageProducer" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="captureDevice" type="{http://www.loc.gov/mix/v20}typeOfCaptureDeviceType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dateTimeCreated",
        "imageProducers",
        "captureDevice"
    })
    public static class GeneralCaptureInformation
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected TypeOfDateType dateTimeCreated;
        @XmlElement(name = "imageProducer")
        protected List<StringType> imageProducers;
        protected TypeOfCaptureDeviceType captureDevice;

        /**
         * Recupera il valore della proprietà dateTimeCreated.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfDateType }
         *     
         */
        public TypeOfDateType getDateTimeCreated() {
            return dateTimeCreated;
        }

        /**
         * Imposta il valore della proprietà dateTimeCreated.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfDateType }
         *     
         */
        public void setDateTimeCreated(TypeOfDateType value) {
            this.dateTimeCreated = value;
        }

        /**
         * Gets the value of the imageProducers property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the imageProducers property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImageProducers().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringType }
         * 
         * 
         */
        public List<StringType> getImageProducers() {
            if (imageProducers == null) {
                imageProducers = new ArrayList<StringType>();
            }
            return this.imageProducers;
        }

        /**
         * Recupera il valore della proprietà captureDevice.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfCaptureDeviceType }
         *     
         */
        public TypeOfCaptureDeviceType getCaptureDevice() {
            return captureDevice;
        }

        /**
         * Imposta il valore della proprietà captureDevice.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfCaptureDeviceType }
         *     
         */
        public void setCaptureDevice(TypeOfCaptureDeviceType value) {
            this.captureDevice = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="scannerManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="ScannerModel" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="scannerModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="scannerModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="scannerModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="MaximumOpticalResolution" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="xOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                   &lt;element name="yOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                   &lt;element name="opticalResolutionUnit" type="{http://www.loc.gov/mix/v20}typeOfOpticalResolutionUnitType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="scannerSensor" type="{http://www.loc.gov/mix/v20}typeOfScannerSensorType" minOccurs="0"/&gt;
     *         &lt;element name="ScanningSystemSoftware" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="scanningSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="scanningSoftwareVersionNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "scannerManufacturer",
        "scannerModel",
        "maximumOpticalResolution",
        "scannerSensor",
        "scanningSystemSoftware"
    })
    public static class ScannerCapture
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType scannerManufacturer;
        @XmlElement(name = "ScannerModel")
        protected ImageCaptureMetadataType.ScannerCapture.ScannerModel scannerModel;
        @XmlElement(name = "MaximumOpticalResolution")
        protected ImageCaptureMetadataType.ScannerCapture.MaximumOpticalResolution maximumOpticalResolution;
        protected TypeOfScannerSensorType scannerSensor;
        @XmlElement(name = "ScanningSystemSoftware")
        protected ImageCaptureMetadataType.ScannerCapture.ScanningSystemSoftware scanningSystemSoftware;

        /**
         * Recupera il valore della proprietà scannerManufacturer.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getScannerManufacturer() {
            return scannerManufacturer;
        }

        /**
         * Imposta il valore della proprietà scannerManufacturer.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setScannerManufacturer(StringType value) {
            this.scannerManufacturer = value;
        }

        /**
         * Recupera il valore della proprietà scannerModel.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.ScannerModel }
         *     
         */
        public ImageCaptureMetadataType.ScannerCapture.ScannerModel getScannerModel() {
            return scannerModel;
        }

        /**
         * Imposta il valore della proprietà scannerModel.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.ScannerModel }
         *     
         */
        public void setScannerModel(ImageCaptureMetadataType.ScannerCapture.ScannerModel value) {
            this.scannerModel = value;
        }

        /**
         * Recupera il valore della proprietà maximumOpticalResolution.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.MaximumOpticalResolution }
         *     
         */
        public ImageCaptureMetadataType.ScannerCapture.MaximumOpticalResolution getMaximumOpticalResolution() {
            return maximumOpticalResolution;
        }

        /**
         * Imposta il valore della proprietà maximumOpticalResolution.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.MaximumOpticalResolution }
         *     
         */
        public void setMaximumOpticalResolution(ImageCaptureMetadataType.ScannerCapture.MaximumOpticalResolution value) {
            this.maximumOpticalResolution = value;
        }

        /**
         * Recupera il valore della proprietà scannerSensor.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfScannerSensorType }
         *     
         */
        public TypeOfScannerSensorType getScannerSensor() {
            return scannerSensor;
        }

        /**
         * Imposta il valore della proprietà scannerSensor.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfScannerSensorType }
         *     
         */
        public void setScannerSensor(TypeOfScannerSensorType value) {
            this.scannerSensor = value;
        }

        /**
         * Recupera il valore della proprietà scanningSystemSoftware.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.ScanningSystemSoftware }
         *     
         */
        public ImageCaptureMetadataType.ScannerCapture.ScanningSystemSoftware getScanningSystemSoftware() {
            return scanningSystemSoftware;
        }

        /**
         * Imposta il valore della proprietà scanningSystemSoftware.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.ScannerCapture.ScanningSystemSoftware }
         *     
         */
        public void setScanningSystemSoftware(ImageCaptureMetadataType.ScannerCapture.ScanningSystemSoftware value) {
            this.scanningSystemSoftware = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="xOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *         &lt;element name="yOpticalResolution" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *         &lt;element name="opticalResolutionUnit" type="{http://www.loc.gov/mix/v20}typeOfOpticalResolutionUnitType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "xOpticalResolution",
            "yOpticalResolution",
            "opticalResolutionUnit"
        })
        public static class MaximumOpticalResolution
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected PositiveIntegerType xOpticalResolution;
            protected PositiveIntegerType yOpticalResolution;
            protected TypeOfOpticalResolutionUnitType opticalResolutionUnit;

            /**
             * Recupera il valore della proprietà xOpticalResolution.
             * 
             * @return
             *     possible object is
             *     {@link PositiveIntegerType }
             *     
             */
            public PositiveIntegerType getXOpticalResolution() {
                return xOpticalResolution;
            }

            /**
             * Imposta il valore della proprietà xOpticalResolution.
             * 
             * @param value
             *     allowed object is
             *     {@link PositiveIntegerType }
             *     
             */
            public void setXOpticalResolution(PositiveIntegerType value) {
                this.xOpticalResolution = value;
            }

            /**
             * Recupera il valore della proprietà yOpticalResolution.
             * 
             * @return
             *     possible object is
             *     {@link PositiveIntegerType }
             *     
             */
            public PositiveIntegerType getYOpticalResolution() {
                return yOpticalResolution;
            }

            /**
             * Imposta il valore della proprietà yOpticalResolution.
             * 
             * @param value
             *     allowed object is
             *     {@link PositiveIntegerType }
             *     
             */
            public void setYOpticalResolution(PositiveIntegerType value) {
                this.yOpticalResolution = value;
            }

            /**
             * Recupera il valore della proprietà opticalResolutionUnit.
             * 
             * @return
             *     possible object is
             *     {@link TypeOfOpticalResolutionUnitType }
             *     
             */
            public TypeOfOpticalResolutionUnitType getOpticalResolutionUnit() {
                return opticalResolutionUnit;
            }

            /**
             * Imposta il valore della proprietà opticalResolutionUnit.
             * 
             * @param value
             *     allowed object is
             *     {@link TypeOfOpticalResolutionUnitType }
             *     
             */
            public void setOpticalResolutionUnit(TypeOfOpticalResolutionUnitType value) {
                this.opticalResolutionUnit = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="scannerModelName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="scannerModelNumber" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="scannerModelSerialNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "scannerModelName",
            "scannerModelNumber",
            "scannerModelSerialNo"
        })
        public static class ScannerModel
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType scannerModelName;
            protected StringType scannerModelNumber;
            protected StringType scannerModelSerialNo;

            /**
             * Recupera il valore della proprietà scannerModelName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getScannerModelName() {
                return scannerModelName;
            }

            /**
             * Imposta il valore della proprietà scannerModelName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setScannerModelName(StringType value) {
                this.scannerModelName = value;
            }

            /**
             * Recupera il valore della proprietà scannerModelNumber.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getScannerModelNumber() {
                return scannerModelNumber;
            }

            /**
             * Imposta il valore della proprietà scannerModelNumber.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setScannerModelNumber(StringType value) {
                this.scannerModelNumber = value;
            }

            /**
             * Recupera il valore della proprietà scannerModelSerialNo.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getScannerModelSerialNo() {
                return scannerModelSerialNo;
            }

            /**
             * Imposta il valore della proprietà scannerModelSerialNo.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setScannerModelSerialNo(StringType value) {
                this.scannerModelSerialNo = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="scanningSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="scanningSoftwareVersionNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "scanningSoftwareName",
            "scanningSoftwareVersionNo"
        })
        public static class ScanningSystemSoftware
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType scanningSoftwareName;
            protected StringType scanningSoftwareVersionNo;

            /**
             * Recupera il valore della proprietà scanningSoftwareName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getScanningSoftwareName() {
                return scanningSoftwareName;
            }

            /**
             * Imposta il valore della proprietà scanningSoftwareName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setScanningSoftwareName(StringType value) {
                this.scanningSoftwareName = value;
            }

            /**
             * Recupera il valore della proprietà scanningSoftwareVersionNo.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getScanningSoftwareVersionNo() {
                return scanningSoftwareVersionNo;
            }

            /**
             * Imposta il valore della proprietà scanningSoftwareVersionNo.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setScanningSoftwareVersionNo(StringType value) {
                this.scanningSoftwareVersionNo = value;
            }

        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="sourceType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="SourceID" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="sourceIDType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="sourceIDValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SourceSize" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="SourceXDimension" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="sourceXDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                             &lt;element name="sourceXDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="SourceYDimension" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="sourceYDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                             &lt;element name="sourceYDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="SourceZDimension" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="sourceZDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
     *                             &lt;element name="sourceZDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sourceType",
        "sourceIDs",
        "sourceSize"
    })
    public static class SourceInformation
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType sourceType;
        @XmlElement(name = "SourceID")
        protected List<ImageCaptureMetadataType.SourceInformation.SourceID> sourceIDs;
        @XmlElement(name = "SourceSize")
        protected ImageCaptureMetadataType.SourceInformation.SourceSize sourceSize;

        /**
         * Recupera il valore della proprietà sourceType.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getSourceType() {
            return sourceType;
        }

        /**
         * Imposta il valore della proprietà sourceType.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setSourceType(StringType value) {
            this.sourceType = value;
        }

        /**
         * Gets the value of the sourceIDs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sourceIDs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSourceIDs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImageCaptureMetadataType.SourceInformation.SourceID }
         * 
         * 
         */
        public List<ImageCaptureMetadataType.SourceInformation.SourceID> getSourceIDs() {
            if (sourceIDs == null) {
                sourceIDs = new ArrayList<ImageCaptureMetadataType.SourceInformation.SourceID>();
            }
            return this.sourceIDs;
        }

        /**
         * Recupera il valore della proprietà sourceSize.
         * 
         * @return
         *     possible object is
         *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize }
         *     
         */
        public ImageCaptureMetadataType.SourceInformation.SourceSize getSourceSize() {
            return sourceSize;
        }

        /**
         * Imposta il valore della proprietà sourceSize.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize }
         *     
         */
        public void setSourceSize(ImageCaptureMetadataType.SourceInformation.SourceSize value) {
            this.sourceSize = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="sourceIDType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="sourceIDValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceIDType",
            "sourceIDValue"
        })
        public static class SourceID
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType sourceIDType;
            protected StringType sourceIDValue;

            /**
             * Recupera il valore della proprietà sourceIDType.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getSourceIDType() {
                return sourceIDType;
            }

            /**
             * Imposta il valore della proprietà sourceIDType.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setSourceIDType(StringType value) {
                this.sourceIDType = value;
            }

            /**
             * Recupera il valore della proprietà sourceIDValue.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getSourceIDValue() {
                return sourceIDValue;
            }

            /**
             * Imposta il valore della proprietà sourceIDValue.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setSourceIDValue(StringType value) {
                this.sourceIDValue = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="SourceXDimension" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="sourceXDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                   &lt;element name="sourceXDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="SourceYDimension" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="sourceYDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                   &lt;element name="sourceYDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="SourceZDimension" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="sourceZDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
         *                   &lt;element name="sourceZDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceXDimension",
            "sourceYDimension",
            "sourceZDimension"
        })
        public static class SourceSize
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "SourceXDimension")
            protected ImageCaptureMetadataType.SourceInformation.SourceSize.SourceXDimension sourceXDimension;
            @XmlElement(name = "SourceYDimension")
            protected ImageCaptureMetadataType.SourceInformation.SourceSize.SourceYDimension sourceYDimension;
            @XmlElement(name = "SourceZDimension")
            protected ImageCaptureMetadataType.SourceInformation.SourceSize.SourceZDimension sourceZDimension;

            /**
             * Recupera il valore della proprietà sourceXDimension.
             * 
             * @return
             *     possible object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceXDimension }
             *     
             */
            public ImageCaptureMetadataType.SourceInformation.SourceSize.SourceXDimension getSourceXDimension() {
                return sourceXDimension;
            }

            /**
             * Imposta il valore della proprietà sourceXDimension.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceXDimension }
             *     
             */
            public void setSourceXDimension(ImageCaptureMetadataType.SourceInformation.SourceSize.SourceXDimension value) {
                this.sourceXDimension = value;
            }

            /**
             * Recupera il valore della proprietà sourceYDimension.
             * 
             * @return
             *     possible object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceYDimension }
             *     
             */
            public ImageCaptureMetadataType.SourceInformation.SourceSize.SourceYDimension getSourceYDimension() {
                return sourceYDimension;
            }

            /**
             * Imposta il valore della proprietà sourceYDimension.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceYDimension }
             *     
             */
            public void setSourceYDimension(ImageCaptureMetadataType.SourceInformation.SourceSize.SourceYDimension value) {
                this.sourceYDimension = value;
            }

            /**
             * Recupera il valore della proprietà sourceZDimension.
             * 
             * @return
             *     possible object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceZDimension }
             *     
             */
            public ImageCaptureMetadataType.SourceInformation.SourceSize.SourceZDimension getSourceZDimension() {
                return sourceZDimension;
            }

            /**
             * Imposta il valore della proprietà sourceZDimension.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageCaptureMetadataType.SourceInformation.SourceSize.SourceZDimension }
             *     
             */
            public void setSourceZDimension(ImageCaptureMetadataType.SourceInformation.SourceSize.SourceZDimension value) {
                this.sourceZDimension = value;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="sourceXDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *         &lt;element name="sourceXDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "sourceXDimensionValue",
                "sourceXDimensionUnit"
            })
            public static class SourceXDimension
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected TypeOfNonNegativeRealType sourceXDimensionValue;
                protected TypeOfsourceDimensionUnitType sourceXDimensionUnit;

                /**
                 * Recupera il valore della proprietà sourceXDimensionValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public TypeOfNonNegativeRealType getSourceXDimensionValue() {
                    return sourceXDimensionValue;
                }

                /**
                 * Imposta il valore della proprietà sourceXDimensionValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public void setSourceXDimensionValue(TypeOfNonNegativeRealType value) {
                    this.sourceXDimensionValue = value;
                }

                /**
                 * Recupera il valore della proprietà sourceXDimensionUnit.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public TypeOfsourceDimensionUnitType getSourceXDimensionUnit() {
                    return sourceXDimensionUnit;
                }

                /**
                 * Imposta il valore della proprietà sourceXDimensionUnit.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public void setSourceXDimensionUnit(TypeOfsourceDimensionUnitType value) {
                    this.sourceXDimensionUnit = value;
                }

            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="sourceYDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *         &lt;element name="sourceYDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "sourceYDimensionValue",
                "sourceYDimensionUnit"
            })
            public static class SourceYDimension
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected TypeOfNonNegativeRealType sourceYDimensionValue;
                protected TypeOfsourceDimensionUnitType sourceYDimensionUnit;

                /**
                 * Recupera il valore della proprietà sourceYDimensionValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public TypeOfNonNegativeRealType getSourceYDimensionValue() {
                    return sourceYDimensionValue;
                }

                /**
                 * Imposta il valore della proprietà sourceYDimensionValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public void setSourceYDimensionValue(TypeOfNonNegativeRealType value) {
                    this.sourceYDimensionValue = value;
                }

                /**
                 * Recupera il valore della proprietà sourceYDimensionUnit.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public TypeOfsourceDimensionUnitType getSourceYDimensionUnit() {
                    return sourceYDimensionUnit;
                }

                /**
                 * Imposta il valore della proprietà sourceYDimensionUnit.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public void setSourceYDimensionUnit(TypeOfsourceDimensionUnitType value) {
                    this.sourceYDimensionUnit = value;
                }

            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="sourceZDimensionValue" type="{http://www.loc.gov/mix/v20}typeOfNonNegativeRealType" minOccurs="0"/&gt;
             *         &lt;element name="sourceZDimensionUnit" type="{http://www.loc.gov/mix/v20}typeOfsourceDimensionUnitType" minOccurs="0"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "sourceZDimensionValue",
                "sourceZDimensionUnit"
            })
            public static class SourceZDimension
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected TypeOfNonNegativeRealType sourceZDimensionValue;
                protected TypeOfsourceDimensionUnitType sourceZDimensionUnit;

                /**
                 * Recupera il valore della proprietà sourceZDimensionValue.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public TypeOfNonNegativeRealType getSourceZDimensionValue() {
                    return sourceZDimensionValue;
                }

                /**
                 * Imposta il valore della proprietà sourceZDimensionValue.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfNonNegativeRealType }
                 *     
                 */
                public void setSourceZDimensionValue(TypeOfNonNegativeRealType value) {
                    this.sourceZDimensionValue = value;
                }

                /**
                 * Recupera il valore della proprietà sourceZDimensionUnit.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public TypeOfsourceDimensionUnitType getSourceZDimensionUnit() {
                    return sourceZDimensionUnit;
                }

                /**
                 * Imposta il valore della proprietà sourceZDimensionUnit.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfsourceDimensionUnitType }
                 *     
                 */
                public void setSourceZDimensionUnit(TypeOfsourceDimensionUnitType value) {
                    this.sourceZDimensionUnit = value;
                }

            }

        }

    }

}
