# cordova-plugin-imagemin

[![npm](https://img.shields.io/npm/v/cordova-plugin-imagemin.svg)](https://www.npmjs.com/package/cordova-plugin-imagemin) 

> A cordova android plugin for image compress.

### Install
```
cordova plugin install cordova-plugin-imagemin;
```

### Usage (Ionic Angular)
// *typings.d.ts*
```
declare var imagemin: any;
```

// *demo.page.ts*
```
const imgData = `/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGC...`;

/**
 * `imagemin.compress()` is the entry method of "cordova-plugin-imagemin".
 *
 * @interface imagemin.compress(
 *    imgBase64: string,
 *    option: {
 *       encodeQuality: number;        
 *       outputImgType: 'jpeg' | 'png';
 *    },
 *    success: (res: { data: string }) => void,
 *    error: (err: Error) => void
 * )
 *
 * @param imgBase64: string;  A base64 string of input image which shoud not starts with `data:image/*;base64,` prefix.
 *                            If it starts with such prefix, please remove the prefix first.
 *
 * @param options: {
 *    bitmapConfig:  'ARGB_8888' | 'ARGB_4444' | 'RGB_565';   default null
 *    encodeQuality: number;                                  0-100, default 60
 *    outputImgType: 'jpeg' | 'png';                          default 'jpeg'
 * }
 *
 * @param success: (res: { data: string }) => void;  The callback param `data` is olso a image base64 string.
 *
 * @param error (err: Error) => void;   Compress failed.
 *
 */
imagemin.compress(imgBase64, { bitmapConfig: 'RGB_565', encodeQuality: 30 }, ({ data }) => {
  console.log(data);
}, (err: any) => {
  console.error(err);
});
```
