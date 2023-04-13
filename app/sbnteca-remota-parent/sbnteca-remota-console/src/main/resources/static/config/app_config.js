app_config = {
  "rootUrl": rootBackend,
  "restAutoCompleteField": rootBackend + "rest/values",
  "restMediaPicker": rootBackend + "rest/resources",
  "restMediaMetadata": rootBackend + "rest/resource/info",
  "restParentChoice": rootBackend + "rest/parent_choice",
  "restServerUrlContent": rootBackend + "rest/editor/content",
  "restServerUrlMedia": rootBackend + "rest/editor/content/resource",
  "restServerUrlSave": rootBackend + "rest/editor/save",
  "restServerSbnRequest": rootBackend + "rest/editor/sbnRequest",
  'magValidationUrl': rootFrontend + "update?validate=",
  "pagination": 12,
  "section_meta": {
    "gen": { "placeholder": "" },
    "bib": { "placeholder": "" },
    "img": { "placeholder": "images/placeholder.png" },
    "audio": { "placeholder": "images/audio_placeholder.png" },
    "video": { "placeholder": "images/video_placeholder.png" },
    // images/pdf_placeholder.png,images/html_placeholder.png
    "doc": { "placeholder": "images/pdf_placeholder.png" },
    "ocr": { "placeholder": "images/ocr_placeholder.png" },
    "dis": { "placeholder": "" }
  },
  "thumbnailMode":"preview",
  "user": userID
};