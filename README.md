# Mon HipHop — แอป Android + iOS (Kotlin Multiplatform + Compose Multiplatform)

โค้ด UI เขียนครั้งเดียวด้วย Kotlin ใน `composeApp/src/commonMain` ใช้ร่วมกันทั้ง Android และ iOS

## โครงสร้าง
- `composeApp/src/commonMain/kotlin/.../App.kt` — หน้าจอทั้งหมด (Hero, ตัวหนังสือวิ่ง, Artists, About, Contact, Footer)
- `.../Data.kt` — **ลิงก์โซเชียลและรายชื่อศิลปิน แก้ที่นี่ที่เดียว**
- `.../Icons.kt` — ไอคอน (path เดียวกับเว็บ), `.../Theme.kt` — สีและสไตล์ตัวอักษร
- `composeApp/src/commonMain/composeResources/drawable/` — รูปและโลโก้
- `composeApp/src/androidMain` — ตัวเปิดแอป Android, `iosApp/` — ตัวเปิดแอป iOS (SwiftUI)

## รัน Android
1. ติดตั้ง Android Studio (รุ่นล่าสุด) แล้วเปิดโฟลเดอร์นี้ (File > Open)
2. ถ้ายังไม่มีไฟล์ `gradlew` ให้รันครั้งเดียวในโฟลเดอร์นี้: `gradle wrapper --gradle-version 8.11.1`
   (หรือสร้างโปรเจกต์ตัวอย่างจาก https://kmp.jetbrains.com แล้วคัดลอก `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar` มาใส่)
3. รอ Gradle Sync แล้วกด Run ที่ configuration `composeApp`

## รัน iOS (ต้องใช้ Mac + Xcode)
1. `brew install xcodegen`
2. `cd iosApp && xcodegen` จะได้ `iosApp.xcodeproj`
3. เปิดไฟล์นั้นใน Xcode เลือก Simulator หรือเครื่องจริง แล้วกด Run
   (Xcode จะเรียก Gradle สร้างเฟรมเวิร์ก Kotlin ให้เอง ต้องมี JDK 17+ และตั้ง Signing Team ถ้าลงเครื่องจริง)

## สิ่งที่ต้องรู้
- ส่วน Releases และ News ถูกตัดออกตามเว็บเวอร์ชันล่าสุด
- ฟอร์ม Contact เป็นแค่เดโมเหมือนในเว็บ (ยังไม่ส่งข้อความจริง) ต้องต่อกับบริการส่งฟอร์มหรือ backend
- ฟอนต์ใช้ฟอนต์ระบบ ถ้าต้องการ Big Shoulders Display / Manrope ให้ใส่ไฟล์ .ttf ที่ `composeResources/font/` แล้วใช้ `Font(Res.font.xxx)`
- เวอร์ชันเครื่องมือใน `gradle/libs.versions.toml` (Kotlin 2.1.0, Compose 1.7.3, AGP 8.7.3) ปรับขึ้นได้ตามที่ Android Studio แนะนำ
- ยังไม่ได้ใส่ไอคอนแอปของ iOS (ใส่ Asset Catalog เพิ่มได้ภายหลัง)
