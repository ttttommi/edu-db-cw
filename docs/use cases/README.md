# Модель прецедентів

## Загальна схема

<center style="
    border-radius:4px;
    border: 1px solid #cfd7e6;
    box-shadow: 0 1px 3px 0 rgba(89,105,129,.05), 0 1px 1px 0 rgba(0,0,0,.025);
    padding: 1em;"
>

@startuml

    actor "Гість" as Guest
    actor "Користувач" as User
    actor "Адміністратор" as Admin

    usecase "<b>Guest.Search<b> \n Пошук інформації" as GS
    usecase "<b>Guest.SignUp<b> \n Зареєструватися в системі" as GSU
    usecase "<b>Guest.SignIn<b> \n Увійти в систему" as GSI
    
    usecase "<b>User.Logout<b> \n Вихід з системи" as UL
    usecase "<b>User.Search<b> \n Пошук інформації користувачем" as US
    usecase "<b>User.Download<b> \n Завантаження файлу \n даних з системи" as UD
    usecase "<b>User.UploadRequest<b> \n Завантаження даних\n користувачем у систему" as UUR
    usecase "<b>User.EditRequest<b> \n Пропонування оновлених даних \n користувачем у систему" as UER
    
    usecase "<b>Admin.Logout<b> \n Вихід з системи" as AL
    usecase "<b>Admin.ChangeUserPermissions<b> \n Зміна доступу до функцій \n системи конкретному користувачеві" as ACUP
    usecase "<b>Admin.ApproveRequest<b> \n Одобрити запит користувача" as AAR
    usecase "<b>Admin.DenyRequest<b> \n Відхилити запит користувача" as ADR
    
    Guest -u-> GS
    Guest -u-> GSU
    Guest -u-> GSI
    
    User -u-> UL
    User -u-> US
    User -r-> UUR
    User -l-> UER
    User -d-> UD
    
    Admin -l-> AL 
    Admin -r-> ACUP 
    Admin -d-> AAR
    Admin -d-> ADR
    
    Admin -u-|> User
    User -u-|> Guest

@enduml

</center>

## Гість

<center style="
    border-radius:4px;
    border: 1px solid #cfd7e6;
    box-shadow: 0 1px 3px 0 rgba(89,105,129,.05), 0 1px 1px 0 rgba(0,0,0,.025);
    padding: 1em;"
>

@startuml

    actor "Гість" as Guest

    usecase "<b>Guest.Search<b> \n Пошук інформації" as GS
    usecase "<b>Guest.SignUp<b> \n Зареєструватися в системі" as GSU
    usecase "<b>Guest.SignIn<b> \n Увійти в систему" as GSI

    Guest -u-> GSU
    Guest -u-> GS
    Guest -u-> GSI

@enduml

</center>

## Користувач

<center style="
    border-radius:4px;
    border: 1px solid #cfd7e6;
    box-shadow: 0 1px 3px 0 rgba(89,105,129,.05), 0 1px 1px 0 rgba(0,0,0,.025);
    padding: 1em;"
>

@startuml

    actor "Користувач" as User

    usecase "<b>User.Logout<b> \n Вихід з системи" as UL
    usecase "<b>User.Search<b> \n Пошук інформації користувачем" as US
    usecase "<b>User.Download<b> \n Завантаження файлу\n даних з системи" as UD
    usecase "<b>User.DataChangeRequest<b> \n Запит користувача \n щодо зміни даних" as UCR
    usecase "<b>User.UploadRequest<b> \n Завантаження даних \n користувачем у систему" as UUR
    usecase "<b>User.EditRequest<b> \n Пропонування оновлення \n даних користувача у систему" as UER

    User -u-> UL
    User -d-> US
    User -l-> UCR
    User -r-> UD

    UUR .u.> UCR:extends
    UER .d.> UCR:extends

@enduml

</center>

## Адміністратор

<center style="
    border-radius:4px;
    border: 1px solid #cfd7e6;
    box-shadow: 0 1px 3px 0 rgba(89,105,129,.05), 0 1px 1px 0 rgba(0,0,0,.025);
    padding: 1em;"
>

@startuml
    
    actor "Адміністратор" as Admin
    
    usecase "<b>Admin.Logout<b> \n Вихід з системи" as AL
    usecase "<b>Admin.ChangeUserPermissions<b> \n Зміна доступу до функцій \n системи конкретному користувачеві" as ACUP
    usecase "<b>Admin.ConsiderRequest<b> \n Розглянути запит користувача" as ACR
    usecase "<b>Admin.ApproveRequest<b> \n Одобрити запит користувача" as AAR
    usecase "<b>Admin.DenyRequest<b> \n Відхилити запит користувача" as ADR
    
    Admin -d-> AL
    Admin -r-> ACUP
    Admin -l-> ACR
    
    AAR .u.> ACR:extends
    ADR .d.> ACR:extends

@enduml

</center>