/**
 * Welcome to your Workbox-powered service worker!
 *
 * You'll need to register this file in your web app and you should
 * disable HTTP caching for this file too.
 * See https://goo.gl/nhQhGp
 *
 * The rest of the code is auto-generated. Please don't update this file
 * directly; instead, make changes to your Workbox build configuration
 * and re-run your build process.
 * See https://goo.gl/2aRDsh
 */

importScripts("https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js");

self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

/**
 * The workboxSW.precacheAndRoute() method efficiently caches and responds to
 * requests for URLs in the manifest.
 * See https://goo.gl/S9QRab
 */
self.__precacheManifest = [
  {
    "url": "03-01.jpg",
    "revision": "61ccd31b9a99e4dc0b2115d7a181dc71"
  },
  {
    "url": "03-02.jpg",
    "revision": "0e42c83dcc174ebfdbb611ab0956fcd9"
  },
  {
    "url": "03-03.jpg",
    "revision": "dd23072447e0798b536bd162235d14c3"
  },
  {
    "url": "03-04.jpg",
    "revision": "661a64ee2776ef94b23ba82f7ee50969"
  },
  {
    "url": "03-05.jpg",
    "revision": "6f52dcb2ccb40af28a65a4b796eb918d"
  },
  {
    "url": "03-06.jpg",
    "revision": "2643698d9412e41a449c1edee719fb29"
  },
  {
    "url": "03-07.jpg",
    "revision": "c4a788f41d803f8b9bddb5adc0513be6"
  },
  {
    "url": "03-08.jpg",
    "revision": "4c810503ec4bc1adfd29904056fc2886"
  },
  {
    "url": "03-09.jpg",
    "revision": "9c798bc91f80f84b6b0e7f7fba26d65b"
  },
  {
    "url": "03-10.jpg",
    "revision": "bb5b18b65591e9d058edbc14b02c6ba5"
  },
  {
    "url": "03-11.jpg",
    "revision": "6a88d8f48c63e79c72ec45ae84a8d9ff"
  },
  {
    "url": "04-01.jpg",
    "revision": "4bbafd5dd4000461cef55f669139b17b"
  },
  {
    "url": "05-01.jpg",
    "revision": "8d54b111a4b6b5fb4cd39e4c7261b927"
  },
  {
    "url": "06-01.jpg",
    "revision": "cf2dab320c48c5ead6fe105863629e4e"
  },
  {
    "url": "06-02.jpg",
    "revision": "aa286f8fd6540b7a7b6d36392a63f505"
  },
  {
    "url": "06-03.jpg",
    "revision": "239fa5fb2a9063f1e261ae2db81fceef"
  },
  {
    "url": "06-04.jpg",
    "revision": "a2dbf924afd6a8580ce4a2d2bcd6e049"
  },
  {
    "url": "06-05.jpg",
    "revision": "22f4f542b283cdc3d027eb664424d3c8"
  },
  {
    "url": "06-06.jpg",
    "revision": "12017ddd5b9179e8f53e93044acee998"
  },
  {
    "url": "06-07.jpg",
    "revision": "1ded65fc56da9f0008ae4ef2eacc8175"
  },
  {
    "url": "1.jpg",
    "revision": "f1ea37a492254cc85dd6fd1e89b1a6b4"
  },
  {
    "url": "1.png",
    "revision": "d41d8cd98f00b204e9800998ecf8427e"
  },
  {
    "url": "12-01.jpg",
    "revision": "cc85570b3c89f73291af87791115986a"
  },
  {
    "url": "12-02.jpg",
    "revision": "f2b24c5c4beb21d5302c64b59445927a"
  },
  {
    "url": "12-03.jpg",
    "revision": "61fe98dff39d0887978deb0af605571b"
  },
  {
    "url": "12-04.jpg",
    "revision": "ff2468e7fbb03e034be0ffd7312dc7d9"
  },
  {
    "url": "12-05.jpg",
    "revision": "a126fe17514d81279c677666fd459d9d"
  },
  {
    "url": "12-06.jpg",
    "revision": "c25686b2f77ac7c5a2d4706f43e40132"
  },
  {
    "url": "12-07.jpg",
    "revision": "de17f3eb1df503250bcf1a5a4b533eec"
  },
  {
    "url": "12-08.jpg",
    "revision": "f3b58c768c18919a6fa2ab59ea6627db"
  },
  {
    "url": "13-01.jpg",
    "revision": "b22f06060909d43d796a8ffd4b0743a0"
  },
  {
    "url": "13-02.jpg",
    "revision": "2d2bca0518716a8dfaf37ef5c15e54ee"
  },
  {
    "url": "13-03.jpg",
    "revision": "fec1a3db903dc05c462fb6f384a400d6"
  },
  {
    "url": "2.jpg",
    "revision": "572bc2e4ef3efb7c81bbbcac5f09147b"
  },
  {
    "url": "3.jpg",
    "revision": "248757985bb49f73624c6923057530ac"
  },
  {
    "url": "4.jpg",
    "revision": "407c52446e2e463c4f8e494d52dbe485"
  },
  {
    "url": "404.html",
    "revision": "984fde18b0bb512259f80051b5a7ae8b"
  },
  {
    "url": "5.jpg",
    "revision": "7e861769b640afa00fcc0ac4b72d9c52"
  },
  {
    "url": "assets/css/0.styles.b38bf22a.css",
    "revision": "693b5e8056cf0081b0b627611119f190"
  },
  {
    "url": "assets/img/auth.345cf7c3.jpg",
    "revision": "345cf7c3478e21075e978406d8db7ea0"
  },
  {
    "url": "assets/img/authA.e330dc6c.jpg",
    "revision": "e330dc6c9574b36afff4318b093fa657"
  },
  {
    "url": "assets/img/get.0faebfd6.jpg",
    "revision": "0faebfd6da4d1685277503885c7cd56e"
  },
  {
    "url": "assets/img/patch.e4c921ac.jpg",
    "revision": "e4c921acd7fab47dc5e941f3b5fe2d6d"
  },
  {
    "url": "assets/img/relational_schema.094ee927.png",
    "revision": "094ee927e01563b4c7f54c42bb73eeb8"
  },
  {
    "url": "assets/img/request.a3509eef.jpg",
    "revision": "a3509eefe4beb55d9b091dbc694a4200"
  },
  {
    "url": "assets/img/search.83621669.svg",
    "revision": "83621669651b9a3d4bf64d1a670ad856"
  },
  {
    "url": "assets/img/user.3c5d1866.jpg",
    "revision": "3c5d18666dbbc128242e105f19061703"
  },
  {
    "url": "assets/js/10.71eaee01.js",
    "revision": "5588e7c747934c7b2356bb151204670b"
  },
  {
    "url": "assets/js/11.b6cbb985.js",
    "revision": "7d59dfac95b8edb33e10514d4dec14f2"
  },
  {
    "url": "assets/js/12.c8e03265.js",
    "revision": "45ec6079e7dc15f7d6bcd45ecb4752c4"
  },
  {
    "url": "assets/js/13.bf238ce5.js",
    "revision": "478f6c9827c1bd579df62f95f083e12a"
  },
  {
    "url": "assets/js/14.4c864c5e.js",
    "revision": "247ab6962c40c076a8240f8512296f87"
  },
  {
    "url": "assets/js/15.73d7025e.js",
    "revision": "fcb55ba3e1e5ffffa4416a288436253d"
  },
  {
    "url": "assets/js/16.9a369fb1.js",
    "revision": "2e8ffc63aca523f6cd975e6b8ab2ff1a"
  },
  {
    "url": "assets/js/17.068d9380.js",
    "revision": "10b5d34eb29d04ee9cd4dec6e6c35236"
  },
  {
    "url": "assets/js/18.47ceacba.js",
    "revision": "86789bca16032b0c604f0223c13e3db6"
  },
  {
    "url": "assets/js/19.3d56db05.js",
    "revision": "88c7be85a96dc0cc35e1ffb380c9cc42"
  },
  {
    "url": "assets/js/2.ab0230cc.js",
    "revision": "4b253f8cf792d08fd2ff856bed1141fd"
  },
  {
    "url": "assets/js/20.b41fdaa3.js",
    "revision": "a5e46ced8cb3c954fe4e2e9e0744fa30"
  },
  {
    "url": "assets/js/21.511cc3cd.js",
    "revision": "2f3a77c2238d736e37965aa28ccd9436"
  },
  {
    "url": "assets/js/22.c08407fa.js",
    "revision": "e49324f8cebfb5b7f76c7b32b6b9b69e"
  },
  {
    "url": "assets/js/23.f99c2183.js",
    "revision": "86d69e3adab2352683dbdeda9f990751"
  },
  {
    "url": "assets/js/24.f132e4b0.js",
    "revision": "3e1e0768d3b2f77422eaba5f12d6a2c4"
  },
  {
    "url": "assets/js/26.3c108c65.js",
    "revision": "14070395307afbb22387ff5bad8d960c"
  },
  {
    "url": "assets/js/3.a5cc1a17.js",
    "revision": "72e0321122cafa940770a9ffe4cfda88"
  },
  {
    "url": "assets/js/4.905087da.js",
    "revision": "d15e044f6bd286c44cd80eaed6f4a572"
  },
  {
    "url": "assets/js/5.152afd75.js",
    "revision": "131e1bcbb72b8e63bcb673f709786383"
  },
  {
    "url": "assets/js/6.26ddc91b.js",
    "revision": "22290ff685029c7e93f0626373b711c9"
  },
  {
    "url": "assets/js/7.8849f760.js",
    "revision": "4f06e4ddd690db0d26edf942bc9bb2fd"
  },
  {
    "url": "assets/js/8.72757196.js",
    "revision": "f85886e22523aaf0a59df17ddebf57f3"
  },
  {
    "url": "assets/js/9.fe3806a7.js",
    "revision": "5be8765a5e59f61518e5466243f8d23f"
  },
  {
    "url": "assets/js/app.d1a6e7bc.js",
    "revision": "44b50529bb0726bb190682662abc6db7"
  },
  {
    "url": "conclusion/index.html",
    "revision": "8d9efedaf8e605d33f7e84a56eead732"
  },
  {
    "url": "design/index.html",
    "revision": "e39cd929ee5c8b90f556b4fe17c8f60a"
  },
  {
    "url": "index.html",
    "revision": "a1ff54721f115637fbc0b7e85bccc59e"
  },
  {
    "url": "intro/index.html",
    "revision": "e7bf10f56744e518443271537d684915"
  },
  {
    "url": "license.html",
    "revision": "70a2edbdcd1a16b29dc6ec11e14ab937"
  },
  {
    "url": "myAvatar.png",
    "revision": "b76db1e62eb8e7fca02a487eb3eac10c"
  },
  {
    "url": "requirements/index.html",
    "revision": "90afd89584c8492fb3ca3523f9d7729a"
  },
  {
    "url": "requirements/stakeholders-needs.html",
    "revision": "dbd58a20e607e242b727d98641ca1984"
  },
  {
    "url": "requirements/state-of-the-art.html",
    "revision": "74622fba454cd80842b484499200dca8"
  },
  {
    "url": "software/index.html",
    "revision": "9c2379b379b637d40d803e2ac8c5bf94"
  },
  {
    "url": "test/index.html",
    "revision": "87c64042c2e6143e485785a4d44e1a1f"
  },
  {
    "url": "use cases/index.html",
    "revision": "d5bc122c41a9ce769f4f9e85d1eb79fa"
  }
].concat(self.__precacheManifest || []);
workbox.precaching.precacheAndRoute(self.__precacheManifest, {});
addEventListener('message', event => {
  const replyPort = event.ports[0]
  const message = event.data
  if (replyPort && message && message.type === 'skip-waiting') {
    event.waitUntil(
      self.skipWaiting().then(
        () => replyPort.postMessage({ error: null }),
        error => replyPort.postMessage({ error })
      )
    )
  }
})
