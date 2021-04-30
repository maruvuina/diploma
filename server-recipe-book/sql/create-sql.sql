CREATE TABLE user
(
    id_user   MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(40) NOT NULL,
    password  VARCHAR(100) NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    avatar_location VARCHAR(100) DEFAULT ('d:\\upload-files\\recipe-system\\user-images\\default-avatar.png'),
    authorise TINYINT NOT NULL
);

CREATE TABLE role
(
    id_role MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(10) NOT NULL
);

CREATE TABLE user_roles
(
    id_user_fk MEDIUMINT NOT NULL,
    id_role_fk MEDIUMINT NOT NULL,
    FOREIGN KEY (id_user_fk) REFERENCES user (id_user),
    FOREIGN KEY (id_role_fk) REFERENCES role (id_role)
);

CREATE TABLE category
(
    id_category MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(20) NOT NULL
);

CREATE TABLE ingredient
(
    id_ingredient MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    ingredient_name VARCHAR(50) NOT NULL
);

CREATE TABLE recipe
(
    id_recipe      MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    recipe_name    VARCHAR(150) NOT NULL,
    cooking_time   MEDIUMINT    NOT NULL,
    yield          MEDIUMINT    NOT NULL,
    main_picture_location   VARCHAR(80) NOT NULL,
    id_user_fk     MEDIUMINT    NOT NULL,
    id_category_fk MEDIUMINT    NOT NULL,
    FOREIGN KEY (id_user_fk) REFERENCES user (id_user),
    FOREIGN KEY (id_category_fk) REFERENCES category (id_category)
);

CREATE TABLE comment
(
    id_comment MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(800) NOT NULL,
    id_recipe_fk MEDIUMINT NOT NULL,
    id_user_fk MEDIUMINT NOT NULL,
    FOREIGN KEY (id_recipe_fk) REFERENCES recipe (id_recipe),
    FOREIGN KEY (id_user_fk) REFERENCES user (id_user)
);

CREATE TABLE recipe_ingredient (
   id_recipe_fk MEDIUMINT NOT NULL,
   id_ingredient_fk MEDIUMINT NOT NULL,
   measure_amount MEDIUMINT NOT NULL,
   FOREIGN KEY (id_recipe_fk) REFERENCES recipe (id_recipe),
   FOREIGN KEY (id_ingredient_fk) REFERENCES ingredient (id_ingredient)
);

CREATE TABLE instruction
(
    id_instruction MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_recipe_fk   MEDIUMINT    NOT NULL,
    step_number    MEDIUMINT    NOT NULL,
    description    VARCHAR(500) NOT NULL,
    FOREIGN KEY (id_recipe_fk) REFERENCES recipe (id_recipe)
);

CREATE TABLE recipe_likes
(
    id_recipe_likes MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_recipe_fk MEDIUMINT NOT NULL,
    id_user_fk MEDIUMINT NOT NULL,
    FOREIGN KEY (id_recipe_fk) REFERENCES recipe (id_recipe),
	FOREIGN KEY (id_user_fk) REFERENCES user (id_user)
);

CREATE TABLE verification_token
(
    id_verification_token MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255),
    expity_date DATETIME,
    user_id_fk MEDIUMINT,
    FOREIGN KEY (user_id_fk) REFERENCES user (id_user)
);

CREATE TABLE refresh_token
(
    id_refresh_token MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255),
    created_date DATETIME
);

CREATE TABLE tag
(
    id_tag MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255) NOT NULL
);

INSERT INTO role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO role (role_name) VALUES ('ROLE_USER');



INSERT INTO category (category_name) VALUES ('Закуски');
INSERT INTO category (category_name) VALUES ('Завтрак');
INSERT INTO category (category_name) VALUES ('Десерты');
INSERT INTO category (category_name) VALUES ('Ужин');
INSERT INTO category (category_name) VALUES ('Напитки');
INSERT INTO category (category_name) VALUES ('Обед');
INSERT INTO category (category_name) VALUES ('Основное блюдо');
INSERT INTO category (category_name) VALUES ('Салаты');
INSERT INTO category (category_name) VALUES ('Супы');
INSERT INTO category (category_name) VALUES ('Овощи');


INSERT INTO ingredient (ingredient_name) VALUES ('Ванилин');
INSERT INTO ingredient (ingredient_name) VALUES ('Разрыхритель');
INSERT INTO ingredient (ingredient_name) VALUES ('Манка');
INSERT INTO ingredient (ingredient_name) VALUES ('Творог');
INSERT INTO ingredient (ingredient_name) VALUES ('Куриное яйцо');
INSERT INTO ingredient (ingredient_name) VALUES ('Сливочное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Изюм');
INSERT INTO ingredient (ingredient_name) VALUES ('Сахар');

INSERT INTO ingredient (ingredient_name) VALUES ('Бекон');
INSERT INTO ingredient (ingredient_name) VALUES ('Ветчина');
INSERT INTO ingredient (ingredient_name) VALUES ('Говядина');
INSERT INTO ingredient (ingredient_name) VALUES ('Заяц');
INSERT INTO ingredient (ingredient_name) VALUES ('Колбаса');
INSERT INTO ingredient (ingredient_name) VALUES ('Колбаски');
INSERT INTO ingredient (ingredient_name) VALUES ('Кролик');
INSERT INTO ingredient (ingredient_name) VALUES ('Мясо');
INSERT INTO ingredient (ingredient_name) VALUES ('Сардельки');
INSERT INTO ingredient (ingredient_name) VALUES ('Свинина');
INSERT INTO ingredient (ingredient_name) VALUES ('Сервелат');
INSERT INTO ingredient (ingredient_name) VALUES ('Сосиски');
INSERT INTO ingredient (ingredient_name) VALUES ('Телятина');
INSERT INTO ingredient (ingredient_name) VALUES ('Фарш');

INSERT INTO ingredient (ingredient_name) VALUES ('Гусь');
INSERT INTO ingredient (ingredient_name) VALUES ('Индейка');
INSERT INTO ingredient (ingredient_name) VALUES ('Курица');
INSERT INTO ingredient (ingredient_name) VALUES ('Перепелка');
INSERT INTO ingredient (ingredient_name) VALUES ('Утка');
INSERT INTO ingredient (ingredient_name) VALUES ('Фуа-гра');
INSERT INTO ingredient (ingredient_name) VALUES ('Цесарка');
INSERT INTO ingredient (ingredient_name) VALUES ('Цыпленок');

INSERT INTO ingredient (ingredient_name) VALUES ('Арахис');
INSERT INTO ingredient (ingredient_name) VALUES ('Грецкие орехи');
INSERT INTO ingredient (ingredient_name) VALUES ('Каштаны');
INSERT INTO ingredient (ingredient_name) VALUES ('Кедровые орехи');
INSERT INTO ingredient (ingredient_name) VALUES ('Кешью');
INSERT INTO ingredient (ingredient_name) VALUES ('Кокос');
INSERT INTO ingredient (ingredient_name) VALUES ('Миндаль');
INSERT INTO ingredient (ingredient_name) VALUES ('Мускатный орех');
INSERT INTO ingredient (ingredient_name) VALUES ('Орехи');
INSERT INTO ingredient (ingredient_name) VALUES ('Орехи макадамия');
INSERT INTO ingredient (ingredient_name) VALUES ('Орехи пинии');
INSERT INTO ingredient (ingredient_name) VALUES ('Пекан');
INSERT INTO ingredient (ingredient_name) VALUES ('Фисташки');
INSERT INTO ingredient (ingredient_name) VALUES ('Фундук');

INSERT INTO ingredient (ingredient_name) VALUES ('Белые грибы');
INSERT INTO ingredient (ingredient_name) VALUES ('Вешенки');
INSERT INTO ingredient (ingredient_name) VALUES ('Грибы');
INSERT INTO ingredient (ingredient_name) VALUES ('Грибы шиитаке');
INSERT INTO ingredient (ingredient_name) VALUES ('Грибы эноки');
INSERT INTO ingredient (ingredient_name) VALUES ('Лисички');
INSERT INTO ingredient (ingredient_name) VALUES ('Маслята');
INSERT INTO ingredient (ingredient_name) VALUES ('Опята');
INSERT INTO ingredient (ingredient_name) VALUES ('Подберезовики');
INSERT INTO ingredient (ingredient_name) VALUES ('Рыжики');
INSERT INTO ingredient (ingredient_name) VALUES ('Сморчки');
INSERT INTO ingredient (ingredient_name) VALUES ('Сухие китайские грибы');
INSERT INTO ingredient (ingredient_name) VALUES ('Трюфели');
INSERT INTO ingredient (ingredient_name) VALUES ('Шампиньоны');

INSERT INTO ingredient (ingredient_name) VALUES ('Базилик');
INSERT INTO ingredient (ingredient_name) VALUES ('Брокколи рааб');
INSERT INTO ingredient (ingredient_name) VALUES ('Букет гарни');
INSERT INTO ingredient (ingredient_name) VALUES ('Вербена лимонная');
INSERT INTO ingredient (ingredient_name) VALUES ('Водяной кресс');
INSERT INTO ingredient (ingredient_name) VALUES ('Джусай');
INSERT INTO ingredient (ingredient_name) VALUES ('Зеленый лук');
INSERT INTO ingredient (ingredient_name) VALUES ('Зеленый салат');
INSERT INTO ingredient (ingredient_name) VALUES ('Зелень');
INSERT INTO ingredient (ingredient_name) VALUES ('Каркаде');
INSERT INTO ingredient (ingredient_name) VALUES ('Кервель');
INSERT INTO ingredient (ingredient_name) VALUES ('Крапива');
INSERT INTO ingredient (ingredient_name) VALUES ('Лавровый лист');
INSERT INTO ingredient (ingredient_name) VALUES ('Лепестки роз');
INSERT INTO ingredient (ingredient_name) VALUES ('Лимонная трава');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья банана');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья боярышника');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья винограда');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья карри');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья малины');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья одуванчика');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья пандан');
INSERT INTO ingredient (ingredient_name) VALUES ('Листья шисо');
INSERT INTO ingredient (ingredient_name) VALUES ('Лук-резанец');
INSERT INTO ingredient (ingredient_name) VALUES ('Майоран');
INSERT INTO ingredient (ingredient_name) VALUES ('Мангольд');
INSERT INTO ingredient (ingredient_name) VALUES ('Мелисса');
INSERT INTO ingredient (ingredient_name) VALUES ('Мизуна');
INSERT INTO ingredient (ingredient_name) VALUES ('Мята');
INSERT INTO ingredient (ingredient_name) VALUES ('Орегано');
INSERT INTO ingredient (ingredient_name) VALUES ('Петрушка');
INSERT INTO ingredient (ingredient_name) VALUES ('Портулак');
INSERT INTO ingredient (ingredient_name) VALUES ('Розмарин');
INSERT INTO ingredient (ingredient_name) VALUES ('Рукола');
INSERT INTO ingredient (ingredient_name) VALUES ('Салат пак-чой');
INSERT INTO ingredient (ingredient_name) VALUES ('Тимьян');
INSERT INTO ingredient (ingredient_name) VALUES ('Укроп');
INSERT INTO ingredient (ingredient_name) VALUES ('Цветы лаванды');
INSERT INTO ingredient (ingredient_name) VALUES ('Цикорий');
INSERT INTO ingredient (ingredient_name) VALUES ('Черемша');
INSERT INTO ingredient (ingredient_name) VALUES ('Шалфей');
INSERT INTO ingredient (ingredient_name) VALUES ('Шпинат');
INSERT INTO ingredient (ingredient_name) VALUES ('Щавель');
INSERT INTO ingredient (ingredient_name) VALUES ('Эстрагон');

INSERT INTO ingredient (ingredient_name) VALUES ('Абрикосовое пюре');
INSERT INTO ingredient (ingredient_name) VALUES ('Абрикос');
INSERT INTO ingredient (ingredient_name) VALUES ('Айва');
INSERT INTO ingredient (ingredient_name) VALUES ('Ананас');
INSERT INTO ingredient (ingredient_name) VALUES ('Апельсин');
INSERT INTO ingredient (ingredient_name) VALUES ('Арбуз');
INSERT INTO ingredient (ingredient_name) VALUES ('Банановое пюре');
INSERT INTO ingredient (ingredient_name) VALUES ('Банан');
INSERT INTO ingredient (ingredient_name) VALUES ('Барбарис');
INSERT INTO ingredient (ingredient_name) VALUES ('Брусника');
INSERT INTO ingredient (ingredient_name) VALUES ('Виноград');
INSERT INTO ingredient (ingredient_name) VALUES ('Вишня');
INSERT INTO ingredient (ingredient_name) VALUES ('Голубика');
INSERT INTO ingredient (ingredient_name) VALUES ('Гранат');
INSERT INTO ingredient (ingredient_name) VALUES ('Грейпфрут');
INSERT INTO ingredient (ingredient_name) VALUES ('Груша');
INSERT INTO ingredient (ingredient_name) VALUES ('Дыня');
INSERT INTO ingredient (ingredient_name) VALUES ('Ежевика');
INSERT INTO ingredient (ingredient_name) VALUES ('Земляника');
INSERT INTO ingredient (ingredient_name) VALUES ('Инжир');
INSERT INTO ingredient (ingredient_name) VALUES ('Карамбола');
INSERT INTO ingredient (ingredient_name) VALUES ('Киви');
INSERT INTO ingredient (ingredient_name) VALUES ('Кизил');
INSERT INTO ingredient (ingredient_name) VALUES ('Клубника');
INSERT INTO ingredient (ingredient_name) VALUES ('Клюква');
INSERT INTO ingredient (ingredient_name) VALUES ('Крыжовник');
INSERT INTO ingredient (ingredient_name) VALUES ('Кумкват');
INSERT INTO ingredient (ingredient_name) VALUES ('Лайм');
INSERT INTO ingredient (ingredient_name) VALUES ('Лимон');
INSERT INTO ingredient (ingredient_name) VALUES ('Личи');
INSERT INTO ingredient (ingredient_name) VALUES ('Малина');
INSERT INTO ingredient (ingredient_name) VALUES ('Манго');
INSERT INTO ingredient (ingredient_name) VALUES ('Мандарин');
INSERT INTO ingredient (ingredient_name) VALUES ('Морошка');
INSERT INTO ingredient (ingredient_name) VALUES ('Мушмула');
INSERT INTO ingredient (ingredient_name) VALUES ('Нектарин');
INSERT INTO ingredient (ingredient_name) VALUES ('Облепиха');
INSERT INTO ingredient (ingredient_name) VALUES ('Папайя');
INSERT INTO ingredient (ingredient_name) VALUES ('Помело');
INSERT INTO ingredient (ingredient_name) VALUES ('Рябина');
INSERT INTO ingredient (ingredient_name) VALUES ('Свежие ягоды');
INSERT INTO ingredient (ingredient_name) VALUES ('Слива');
INSERT INTO ingredient (ingredient_name) VALUES ('Смородина');
INSERT INTO ingredient (ingredient_name) VALUES ('Танжело');
INSERT INTO ingredient (ingredient_name) VALUES ('Фейхоа');
INSERT INTO ingredient (ingredient_name) VALUES ('Финики');
INSERT INTO ingredient (ingredient_name) VALUES ('Хурма');
INSERT INTO ingredient (ingredient_name) VALUES ('Черника');
INSERT INTO ingredient (ingredient_name) VALUES ('Чернослив');
INSERT INTO ingredient (ingredient_name) VALUES ('Яблоко');

INSERT INTO ingredient (ingredient_name) VALUES ('Амаретти');
INSERT INTO ingredient (ingredient_name) VALUES ('Багель');
INSERT INTO ingredient (ingredient_name) VALUES ('Батон');
INSERT INTO ingredient (ingredient_name) VALUES ('Безе');
INSERT INTO ingredient (ingredient_name) VALUES ('Бисквит');
INSERT INTO ingredient (ingredient_name) VALUES ('Блины');
INSERT INTO ingredient (ingredient_name) VALUES ('Бублик');
INSERT INTO ingredient (ingredient_name) VALUES ('Булочка');
INSERT INTO ingredient (ingredient_name) VALUES ('Бульон');
INSERT INTO ingredient (ingredient_name) VALUES ('Вафли');
INSERT INTO ingredient (ingredient_name) VALUES ('Вода');
INSERT INTO ingredient (ingredient_name) VALUES ('Галеты');
INSERT INTO ingredient (ingredient_name) VALUES ('Гренки');
INSERT INTO ingredient (ingredient_name) VALUES ('Грильяж');
INSERT INTO ingredient (ingredient_name) VALUES ('Гуакамоле');
INSERT INTO ingredient (ingredient_name) VALUES ('Дрожжевое тесто');
INSERT INTO ingredient (ingredient_name) VALUES ('Желе');
INSERT INTO ingredient (ingredient_name) VALUES ('Заварной крем');
INSERT INTO ingredient (ingredient_name) VALUES ('Зефир');
INSERT INTO ingredient (ingredient_name) VALUES ('Ирис');
INSERT INTO ingredient (ingredient_name) VALUES ('Итальянская лепешка');
INSERT INTO ingredient (ingredient_name) VALUES ('Квас хлебный');
INSERT INTO ingredient (ingredient_name) VALUES ('Кекс английский');
INSERT INTO ingredient (ingredient_name) VALUES ('Кока-кола');
INSERT INTO ingredient (ingredient_name) VALUES ('Крекеры');
INSERT INTO ingredient (ingredient_name) VALUES ('Куриный бульон');
INSERT INTO ingredient (ingredient_name) VALUES ('Лед');
INSERT INTO ingredient (ingredient_name) VALUES ('Лимонад');
INSERT INTO ingredient (ingredient_name) VALUES ('Лимонный крем');
INSERT INTO ingredient (ingredient_name) VALUES ('Маленькие ириски');
INSERT INTO ingredient (ingredient_name) VALUES ('Маца');
INSERT INTO ingredient (ingredient_name) VALUES ('Миндальные макаруны');
INSERT INTO ingredient (ingredient_name) VALUES ('Мисо суп');
INSERT INTO ingredient (ingredient_name) VALUES ('Мятная карамель');
INSERT INTO ingredient (ingredient_name) VALUES ('Панеттоне');
INSERT INTO ingredient (ingredient_name) VALUES ('Паштет');
INSERT INTO ingredient (ingredient_name) VALUES ('Песочное тесто');
INSERT INTO ingredient (ingredient_name) VALUES ('Песто');
INSERT INTO ingredient (ingredient_name) VALUES ('Печенье');
INSERT INTO ingredient (ingredient_name) VALUES ('Пико де галло');
INSERT INTO ingredient (ingredient_name) VALUES ('Пита');
INSERT INTO ingredient (ingredient_name) VALUES ('Пряники');
INSERT INTO ingredient (ingredient_name) VALUES ('Пумперникель');
INSERT INTO ingredient (ingredient_name) VALUES ('Разноцветная карамель');
INSERT INTO ingredient (ingredient_name) VALUES ('Сацебели');
INSERT INTO ingredient (ingredient_name) VALUES ('Сливочный соус');
INSERT INTO ingredient (ingredient_name) VALUES ('Слоеное тесто');
INSERT INTO ingredient (ingredient_name) VALUES ('Соевый йогурт');
INSERT INTO ingredient (ingredient_name) VALUES ('Соус');
INSERT INTO ingredient (ingredient_name) VALUES ('Табуле');
INSERT INTO ingredient (ingredient_name) VALUES ('Тапенад');
INSERT INTO ingredient (ingredient_name) VALUES ('Тарталетки');
INSERT INTO ingredient (ingredient_name) VALUES ('Тахини');
INSERT INTO ingredient (ingredient_name) VALUES ('Тесто');
INSERT INTO ingredient (ingredient_name) VALUES ('Тортильи');
INSERT INTO ingredient (ingredient_name) VALUES ('Французский багет');
INSERT INTO ingredient (ingredient_name) VALUES ('Хлебные палочки');
INSERT INTO ingredient (ingredient_name) VALUES ('Шоколадный женуаз');
INSERT INTO ingredient (ingredient_name) VALUES ('Шоколадный соус');

INSERT INTO ingredient (ingredient_name) VALUES ('Адыгейский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Голландский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Голубой сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Зеленый сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Имеретинский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Козий сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Овечий сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Пошехонский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Российский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Сливочный сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр асьяго');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр базирон песто');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр банон');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр блю уэнслидейл');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр бри');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр брынза');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр буррата');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр бурсен');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр гауда');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр горгонзола');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр грана-падано');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр грюйер');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр дабл глостер');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр дор-блю');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр камамбер');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр конте');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр маскарпоне');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр моцарелла');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр надуги');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр пармезан');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр пекорино романо');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр проволоне');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр рокфор');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр скаморца');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр сулугуни');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр фета');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр фетакса');
INSERT INTO ingredient (ingredient_name) VALUES ('Cыр филадельфия');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр халуми');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр чеддер');
INSERT INTO ingredient (ingredient_name) VALUES ('Сыр эдам');
INSERT INTO ingredient (ingredient_name) VALUES ('Сырная косичка');
INSERT INTO ingredient (ingredient_name) VALUES ('Швейцарский сыр');
INSERT INTO ingredient (ingredient_name) VALUES ('Шоколадный сыр');

INSERT INTO ingredient (ingredient_name) VALUES ('Артишоки');
INSERT INTO ingredient (ingredient_name) VALUES ('Баклажаны');
INSERT INTO ingredient (ingredient_name) VALUES ('Бамия');
INSERT INTO ingredient (ingredient_name) VALUES ('Батат');
INSERT INTO ingredient (ingredient_name) VALUES ('Белокочанная капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Брюква');
INSERT INTO ingredient (ingredient_name) VALUES ('Брюссельская капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Галангал');
INSERT INTO ingredient (ingredient_name) VALUES ('Горох');
INSERT INTO ingredient (ingredient_name) VALUES ('Джикама');
INSERT INTO ingredient (ingredient_name) VALUES ('Зеленый горошек');
INSERT INTO ingredient (ingredient_name) VALUES ('Имбирь');
INSERT INTO ingredient (ingredient_name) VALUES ('Кабачки');
INSERT INTO ingredient (ingredient_name) VALUES ('Кайенский перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Каперсы');
INSERT INTO ingredient (ingredient_name) VALUES ('Капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Капуста брокколи');
INSERT INTO ingredient (ingredient_name) VALUES ('Капуста романеско');
INSERT INTO ingredient (ingredient_name) VALUES ('Картофель');
INSERT INTO ingredient (ingredient_name) VALUES ('Китайская капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Кольраби');
INSERT INTO ingredient (ingredient_name) VALUES ('Консервированный перец пименто');
INSERT INTO ingredient (ingredient_name) VALUES ('Корень куркумы');
INSERT INTO ingredient (ingredient_name) VALUES ('Коренья');
INSERT INTO ingredient (ingredient_name) VALUES ('Краснокочанная капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Красный лук');
INSERT INTO ingredient (ingredient_name) VALUES ('Крачай');
INSERT INTO ingredient (ingredient_name) VALUES ('Лук-порей');
INSERT INTO ingredient (ingredient_name) VALUES ('Лук-шалот');
INSERT INTO ingredient (ingredient_name) VALUES ('Маринованные огурцы');
INSERT INTO ingredient (ingredient_name) VALUES ('Маслины');
INSERT INTO ingredient (ingredient_name) VALUES ('Молодые плоды люфы');
INSERT INTO ingredient (ingredient_name) VALUES ('Морковь');
INSERT INTO ingredient (ingredient_name) VALUES ('Овощи');
INSERT INTO ingredient (ingredient_name) VALUES ('Огурцы');
INSERT INTO ingredient (ingredient_name) VALUES ('Оливки');
INSERT INTO ingredient (ingredient_name) VALUES ('Очищенный консервированный перец пикийо');
INSERT INTO ingredient (ingredient_name) VALUES ('Пальчиковые помидоры');
INSERT INTO ingredient (ingredient_name) VALUES ('Пастернак');
INSERT INTO ingredient (ingredient_name) VALUES ('Патиссоны');
INSERT INTO ingredient (ingredient_name) VALUES ('Перец чили');
INSERT INTO ingredient (ingredient_name) VALUES ('Помидоры');
INSERT INTO ingredient (ingredient_name) VALUES ('Помидоры черри');
INSERT INTO ingredient (ingredient_name) VALUES ('Ревень');
INSERT INTO ingredient (ingredient_name) VALUES ('Редис');
INSERT INTO ingredient (ingredient_name) VALUES ('Редька');
INSERT INTO ingredient (ingredient_name) VALUES ('Репа');
INSERT INTO ingredient (ingredient_name) VALUES ('Репчатый лук');
INSERT INTO ingredient (ingredient_name) VALUES ('Савойская капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Свежая кукуруза');
INSERT INTO ingredient (ingredient_name) VALUES ('Свежий перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Свекла');
INSERT INTO ingredient (ingredient_name) VALUES ('Сельдерей');
INSERT INTO ingredient (ingredient_name) VALUES ('Сладкий перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Смесь проростков');
INSERT INTO ingredient (ingredient_name) VALUES ('Спаржа');
INSERT INTO ingredient (ingredient_name) VALUES ('Стручковая фасоль');
INSERT INTO ingredient (ingredient_name) VALUES ('Топинамбур');
INSERT INTO ingredient (ingredient_name) VALUES ('Тыква');
INSERT INTO ingredient (ingredient_name) VALUES ('Фенхель');
INSERT INTO ingredient (ingredient_name) VALUES ('Хрен');
INSERT INTO ingredient (ingredient_name) VALUES ('Цветная капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Цукини');
INSERT INTO ingredient (ingredient_name) VALUES ('Чеснок');
INSERT INTO ingredient (ingredient_name) VALUES ('Ямс');

INSERT INTO ingredient (ingredient_name) VALUES ('Йогурт');
INSERT INTO ingredient (ingredient_name) VALUES ('Кварк');
INSERT INTO ingredient (ingredient_name) VALUES ('Кефир');
INSERT INTO ingredient (ingredient_name) VALUES ('Маргарин');
INSERT INTO ingredient (ingredient_name) VALUES ('Молоко');
INSERT INTO ingredient (ingredient_name) VALUES ('Мороженое');
INSERT INTO ingredient (ingredient_name) VALUES ('Обезжиренный творог');
INSERT INTO ingredient (ingredient_name) VALUES ('Пахта');
INSERT INTO ingredient (ingredient_name) VALUES ('Перепелиное яйцо');
INSERT INTO ingredient (ingredient_name) VALUES ('Простокваша');
INSERT INTO ingredient (ingredient_name) VALUES ('Ряженка');
INSERT INTO ingredient (ingredient_name) VALUES ('Сгущенное молоко');
INSERT INTO ingredient (ingredient_name) VALUES ('Сливки');
INSERT INTO ingredient (ingredient_name) VALUES ('Сметана');
INSERT INTO ingredient (ingredient_name) VALUES ('Утиное яйцо');

INSERT INTO ingredient (ingredient_name) VALUES ('Адинамодо');
INSERT INTO ingredient (ingredient_name) VALUES ('Ажгон');
INSERT INTO ingredient (ingredient_name) VALUES ('Амчур');
INSERT INTO ingredient (ingredient_name) VALUES ('Белый перец горошком');
INSERT INTO ingredient (ingredient_name) VALUES ('Бульонный кубик');
INSERT INTO ingredient (ingredient_name) VALUES ('Ваниль');
INSERT INTO ingredient (ingredient_name) VALUES ('Васаби');
INSERT INTO ingredient (ingredient_name) VALUES ('Гвоздика');
INSERT INTO ingredient (ingredient_name) VALUES ('Длинный черный перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Заатар');
INSERT INTO ingredient (ingredient_name) VALUES ('Зеленый перец горошком');
INSERT INTO ingredient (ingredient_name) VALUES ('Кардамон');
INSERT INTO ingredient (ingredient_name) VALUES ('Карри');
INSERT INTO ingredient (ingredient_name) VALUES ('Кора дуба');
INSERT INTO ingredient (ingredient_name) VALUES ('Корица');
INSERT INTO ingredient (ingredient_name) VALUES ('Кунжутные семечки');
INSERT INTO ingredient (ingredient_name) VALUES ('Куркума');
INSERT INTO ingredient (ingredient_name) VALUES ('Мак');
INSERT INTO ingredient (ingredient_name) VALUES ('Масала');
INSERT INTO ingredient (ingredient_name) VALUES ('Паприка');
INSERT INTO ingredient (ingredient_name) VALUES ('Пассифлора');
INSERT INTO ingredient (ingredient_name) VALUES ('Пекарский порошок');
INSERT INTO ingredient (ingredient_name) VALUES ('Пряности');
INSERT INTO ingredient (ingredient_name) VALUES ('Розовый перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Салатная заправка');
INSERT INTO ingredient (ingredient_name) VALUES ('Семена горчицы');
INSERT INTO ingredient (ingredient_name) VALUES ('Семена кориандра');
INSERT INTO ingredient (ingredient_name) VALUES ('Семечки подсолнуха');
INSERT INTO ingredient (ingredient_name) VALUES ('Смесь «Четыре специи»');
INSERT INTO ingredient (ingredient_name) VALUES ('Смесь итальянских трав');
INSERT INTO ingredient (ingredient_name) VALUES ('Смесь перцев');
INSERT INTO ingredient (ingredient_name) VALUES ('Соль');
INSERT INTO ingredient (ingredient_name) VALUES ('Соус хойсин');
INSERT INTO ingredient (ingredient_name) VALUES ('Сычуаньский перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Тандури');
INSERT INTO ingredient (ingredient_name) VALUES ('Черный душистый перец');
INSERT INTO ingredient (ingredient_name) VALUES ('Черный перец горошком');
INSERT INTO ingredient (ingredient_name) VALUES ('Шафран');

INSERT INTO ingredient (ingredient_name) VALUES ('Анчоусы');
INSERT INTO ingredient (ingredient_name) VALUES ('Барабулька');
INSERT INTO ingredient (ingredient_name) VALUES ('Баррамунди');
INSERT INTO ingredient (ingredient_name) VALUES ('Белая акула');
INSERT INTO ingredient (ingredient_name) VALUES ('Белуга');
INSERT INTO ingredient (ingredient_name) VALUES ('Голец');
INSERT INTO ingredient (ingredient_name) VALUES ('Горбуша');
INSERT INTO ingredient (ingredient_name) VALUES ('Гребешки');
INSERT INTO ingredient (ingredient_name) VALUES ('Дорадо');
INSERT INTO ingredient (ingredient_name) VALUES ('Икра');
INSERT INTO ingredient (ingredient_name) VALUES ('Кальмары');
INSERT INTO ingredient (ingredient_name) VALUES ('Камбала');
INSERT INTO ingredient (ingredient_name) VALUES ('Каракатица');
INSERT INTO ingredient (ingredient_name) VALUES ('Карась');
INSERT INTO ingredient (ingredient_name) VALUES ('Карп');
INSERT INTO ingredient (ingredient_name) VALUES ('Кефаль');
INSERT INTO ingredient (ingredient_name) VALUES ('Килька');
INSERT INTO ingredient (ingredient_name) VALUES ('Крабы');
INSERT INTO ingredient (ingredient_name) VALUES ('Креветки');
INSERT INTO ingredient (ingredient_name) VALUES ('Лангуст');
INSERT INTO ingredient (ingredient_name) VALUES ('Лангустины');
INSERT INTO ingredient (ingredient_name) VALUES ('Лобстер');
INSERT INTO ingredient (ingredient_name) VALUES ('Лосось');
INSERT INTO ingredient (ingredient_name) VALUES ('Макрель');
INSERT INTO ingredient (ingredient_name) VALUES ('Мидии');
INSERT INTO ingredient (ingredient_name) VALUES ('Миноги');
INSERT INTO ingredient (ingredient_name) VALUES ('Минтай');
INSERT INTO ingredient (ingredient_name) VALUES ('Мойва');
INSERT INTO ingredient (ingredient_name) VALUES ('Моллюски');
INSERT INTO ingredient (ingredient_name) VALUES ('Морской лещ');
INSERT INTO ingredient (ingredient_name) VALUES ('Морской язык');
INSERT INTO ingredient (ingredient_name) VALUES ('Окунь');
INSERT INTO ingredient (ingredient_name) VALUES ('Осетрина');
INSERT INTO ingredient (ingredient_name) VALUES ('Осьминог');
INSERT INTO ingredient (ingredient_name) VALUES ('Палтус');
INSERT INTO ingredient (ingredient_name) VALUES ('Раки');
INSERT INTO ingredient (ingredient_name) VALUES ('Сазан');
INSERT INTO ingredient (ingredient_name) VALUES ('Сайда');
INSERT INTO ingredient (ingredient_name) VALUES ('Сардины');
INSERT INTO ingredient (ingredient_name) VALUES ('Севрюга');
INSERT INTO ingredient (ingredient_name) VALUES ('Сельдь');
INSERT INTO ingredient (ingredient_name) VALUES ('Семга');
INSERT INTO ingredient (ingredient_name) VALUES ('Сибас');
INSERT INTO ingredient (ingredient_name) VALUES ('Скумбрия');
INSERT INTO ingredient (ingredient_name) VALUES ('Сом');
INSERT INTO ingredient (ingredient_name) VALUES ('Сушеные снетки');
INSERT INTO ingredient (ingredient_name) VALUES ('Треска');
INSERT INTO ingredient (ingredient_name) VALUES ('Тунец');
INSERT INTO ingredient (ingredient_name) VALUES ('Угорь');
INSERT INTO ingredient (ingredient_name) VALUES ('Филе ската');
INSERT INTO ingredient (ingredient_name) VALUES ('Форель');
INSERT INTO ingredient (ingredient_name) VALUES ('Щука');

INSERT INTO ingredient (ingredient_name) VALUES ('Бобы');
INSERT INTO ingredient (ingredient_name) VALUES ('Геркулес');
INSERT INTO ingredient (ingredient_name) VALUES ('Гречневая крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Гречневая мука');
INSERT INTO ingredient (ingredient_name) VALUES ('Дикий рис');
INSERT INTO ingredient (ingredient_name) VALUES ('Какао');
INSERT INTO ingredient (ingredient_name) VALUES ('Камут');
INSERT INTO ingredient (ingredient_name) VALUES ('Киноа');
INSERT INTO ingredient (ingredient_name) VALUES ('Кукурузная мука');
INSERT INTO ingredient (ingredient_name) VALUES ('Кускус');
INSERT INTO ingredient (ingredient_name) VALUES ('Льняная мука');
INSERT INTO ingredient (ingredient_name) VALUES ('Манная крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Мука из киноа');
INSERT INTO ingredient (ingredient_name) VALUES ('Натуральный кофе');
INSERT INTO ingredient (ingredient_name) VALUES ('Нут');
INSERT INTO ingredient (ingredient_name) VALUES ('Овсяная крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Отруби');
INSERT INTO ingredient (ingredient_name) VALUES ('Перловая крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Полба');
INSERT INTO ingredient (ingredient_name) VALUES ('Пшеничная крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Пшеничная мука');
INSERT INTO ingredient (ingredient_name) VALUES ('Пшено');
INSERT INTO ingredient (ingredient_name) VALUES ('Ржаная крупа');
INSERT INTO ingredient (ingredient_name) VALUES ('Рис');
INSERT INTO ingredient (ingredient_name) VALUES ('Рисовая мука');
INSERT INTO ingredient (ingredient_name) VALUES ('Саго');
INSERT INTO ingredient (ingredient_name) VALUES ('Фасоль');
INSERT INTO ingredient (ingredient_name) VALUES ('Чечевица');
INSERT INTO ingredient (ingredient_name) VALUES ('Шоколад');
INSERT INTO ingredient (ingredient_name) VALUES ('Ячмень');

INSERT INTO ingredient (ingredient_name) VALUES ('Абсент');
INSERT INTO ingredient (ingredient_name) VALUES ('Ангостура');
INSERT INTO ingredient (ingredient_name) VALUES ('Арак');
INSERT INTO ingredient (ingredient_name) VALUES ('Арманьяк');
INSERT INTO ingredient (ingredient_name) VALUES ('Бальзам');
INSERT INTO ingredient (ingredient_name) VALUES ('Бренди');
INSERT INTO ingredient (ingredient_name) VALUES ('Бурбон');
INSERT INTO ingredient (ingredient_name) VALUES ('Вермут');
INSERT INTO ingredient (ingredient_name) VALUES ('Вино');
INSERT INTO ingredient (ingredient_name) VALUES ('Виски');
INSERT INTO ingredient (ingredient_name) VALUES ('Граппа');
INSERT INTO ingredient (ingredient_name) VALUES ('Имбирное зеленое вино');
INSERT INTO ingredient (ingredient_name) VALUES ('Кава');
INSERT INTO ingredient (ingredient_name) VALUES ('Кагор');
INSERT INTO ingredient (ingredient_name) VALUES ('Кальвадос');
INSERT INTO ingredient (ingredient_name) VALUES ('Кашаса');
INSERT INTO ingredient (ingredient_name) VALUES ('Коньяк');
INSERT INTO ingredient (ingredient_name) VALUES ('Ламбруско');
INSERT INTO ingredient (ingredient_name) VALUES ('Мадера');
INSERT INTO ingredient (ingredient_name) VALUES ('Марсала');
INSERT INTO ingredient (ingredient_name) VALUES ('Мартини');
INSERT INTO ingredient (ingredient_name) VALUES ('Мускат');
INSERT INTO ingredient (ingredient_name) VALUES ('Оранж биттер');
INSERT INTO ingredient (ingredient_name) VALUES ('Пастис');
INSERT INTO ingredient (ingredient_name) VALUES ('Перно');
INSERT INTO ingredient (ingredient_name) VALUES ('Портвейн');
INSERT INTO ingredient (ingredient_name) VALUES ('Ром');
INSERT INTO ingredient (ingredient_name) VALUES ('Рутбир');
INSERT INTO ingredient (ingredient_name) VALUES ('Саке');
INSERT INTO ingredient (ingredient_name) VALUES ('Самбука');
INSERT INTO ingredient (ingredient_name) VALUES ('Сидр');
INSERT INTO ingredient (ingredient_name) VALUES ('Херес');

INSERT INTO ingredient (ingredient_name) VALUES ('Аджика');
INSERT INTO ingredient (ingredient_name) VALUES ('Арахисовое масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Бальзамический уксус');
INSERT INTO ingredient (ingredient_name) VALUES ('Варенье');
INSERT INTO ingredient (ingredient_name) VALUES ('Водоросли');
INSERT INTO ingredient (ingredient_name) VALUES ('Горчица');
INSERT INTO ingredient (ingredient_name) VALUES ('Джем');
INSERT INTO ingredient (ingredient_name) VALUES ('Желатин');
INSERT INTO ingredient (ingredient_name) VALUES ('Имбирное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Кетчуп');
INSERT INTO ingredient (ingredient_name) VALUES ('Кислота лимонная');
INSERT INTO ingredient (ingredient_name) VALUES ('Кленовый сироп');
INSERT INTO ingredient (ingredient_name) VALUES ('Клетчатка');
INSERT INTO ingredient (ingredient_name) VALUES ('Кокосовое молоко');
INSERT INTO ingredient (ingredient_name) VALUES ('Кондитерские украшения');
INSERT INTO ingredient (ingredient_name) VALUES ('Конфеты');
INSERT INTO ingredient (ingredient_name) VALUES ('Конфитюр');
INSERT INTO ingredient (ingredient_name) VALUES ('Коптильная жидкость');
INSERT INTO ingredient (ingredient_name) VALUES ('Крахмал');
INSERT INTO ingredient (ingredient_name) VALUES ('Кукурузное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Кунжутное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Лапша');
INSERT INTO ingredient (ingredient_name) VALUES ('Майонез');
INSERT INTO ingredient (ingredient_name) VALUES ('Марципан');
INSERT INTO ingredient (ingredient_name) VALUES ('Масло авокадо');
INSERT INTO ingredient (ingredient_name) VALUES ('Масло для фритюра');
INSERT INTO ingredient (ingredient_name) VALUES ('Масло из виноградных косточек');
INSERT INTO ingredient (ingredient_name) VALUES ('Мед');
INSERT INTO ingredient (ingredient_name) VALUES ('Миндальное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Мюсли');
INSERT INTO ingredient (ingredient_name) VALUES ('Оливковое масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Ореховое масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Панировочные сухари');
INSERT INTO ingredient (ingredient_name) VALUES ('Паста');
INSERT INTO ingredient (ingredient_name) VALUES ('Пищевой краситель');
INSERT INTO ingredient (ingredient_name) VALUES ('Повидло');
INSERT INTO ingredient (ingredient_name) VALUES ('Помидоры в собственном соку');
INSERT INTO ingredient (ingredient_name) VALUES ('Растительное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Растительные сливки');
INSERT INTO ingredient (ingredient_name) VALUES ('Рафинированное масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Свежие дрожжи');
INSERT INTO ingredient (ingredient_name) VALUES ('Соевый соус');
INSERT INTO ingredient (ingredient_name) VALUES ('Томатная паста');
INSERT INTO ingredient (ingredient_name) VALUES ('Тофу');
INSERT INTO ingredient (ingredient_name) VALUES ('Уксус');
INSERT INTO ingredient (ingredient_name) VALUES ('Фанта');
INSERT INTO ingredient (ingredient_name) VALUES ('Фруктовый мармелад');
INSERT INTO ingredient (ingredient_name) VALUES ('Фруктоза');
INSERT INTO ingredient (ingredient_name) VALUES ('Хлопковое масло');
INSERT INTO ingredient (ingredient_name) VALUES ('Цукаты');
INSERT INTO ingredient (ingredient_name) VALUES ('Черная лакрица');
INSERT INTO ingredient (ingredient_name) VALUES ('Шоколадная стружка');
INSERT INTO ingredient (ingredient_name) VALUES ('Куриное филе');
INSERT INTO ingredient (ingredient_name) VALUES ('Пекинская капуста');
INSERT INTO ingredient (ingredient_name) VALUES ('Сода');


