function scroll_to(clicked_link, nav_height) {
    var element_class = clicked_link.attr("href").replace("#", ".");
    var scroll_to = 0;
    if (element_class != ".top-content") {
        element_class += "-container";
        scroll_to = $(element_class).offset().top - nav_height;
    }
    if ($(window).scrollTop() != scroll_to) {
        $("html, body").stop().animate({scrollTop: scroll_to}, 1000);
    }
}

jQuery(document).ready(function () {
    /*
          Sidebar
      */
    $(".dismiss, .overlay").on("click", function () {
        $(".sidebar").removeClass("active");
        $(".overlay").removeClass("active");
    });

    $(".open-menu").on("click", function (e) {
        e.preventDefault();
        $(".sidebar").addClass("active");
        $(".overlay").addClass("active");
        // close opened sub-menus
        $(".collapse.show").toggleClass("show");
        $("a[aria-expanded=true]").attr("aria-expanded", "false");
    });
    /* change sidebar style */
    $("a.btn-customized-dark").on("click", function (e) {
        e.preventDefault();
        $(".sidebar").removeClass("light");
    });
    $("a.btn-customized-light").on("click", function (e) {
        e.preventDefault();
        $(".sidebar").addClass("light");
    });
    /* replace the default browser scrollbar in the sidebar, in case the sidebar menu has a height that is bigger than the viewport */
    $(".sidebar").mCustomScrollbar({
        theme: "minimal-dark",
    });

    /*
          Navigation
      */
    $("a.scroll-link").on("click", function (e) {
        e.preventDefault();
        scroll_to($(this), 0);
    });

    $(".to-top a").on("click", function (e) {
        e.preventDefault();
        if ($(window).scrollTop() != 0) {
            $("html, body").stop().animate({scrollTop: 0}, 1000);
        }
    });
    /* make the active menu item change color as the page scrolls up and down */
    /* we add 2 waypoints for each direction "up/down" with different offsets, because the "up" direction doesn't work with only one waypoint */
    $(".section-container").waypoint(
        function (direction) {
            if (direction === "down") {
                $(".menu-elements li").removeClass("active");
                $('.menu-elements a[href="#' + this.element.id + '"]')
                    .parents("li")
                    .addClass("active");
                //console.log(this.element.id + ' hit, direction ' + direction);
            }
        },
        {
            offset: "0",
        }
    );
    $(".section-container").waypoint(
        function (direction) {
            if (direction === "up") {
                $(".menu-elements li").removeClass("active");
                $('.menu-elements a[href="#' + this.element.id + '"]')
                    .parents("li")
                    .addClass("active");
                //console.log(this.element.id + ' hit, direction ' + direction);
            }
        },
        {
            offset: "-5",
        }
    );

    /*
          Background slideshow
      */
    $(".top-content").backstretch(
        "../static/assets/img/banner.jpg"
    );
    /*
          Wow
      */
    new WOW().init();

    /*
          Contact form
      */
    $(
        '.section-6-form form input[type="text"], .section-6-form form textarea'
    ).on("focus", function () {
        $(
            '.section-6-form form input[type="text"], .section-6-form form textarea'
        ).removeClass("input-error");
    });
    $(".section-6-form form").submit(function (e) {
        e.preventDefault();
        $(
            '.section-6-form form input[type="text"], .section-6-form form textarea'
        ).removeClass("input-error");
        var postdata = $(".section-6-form form").serialize();
        $.ajax({
            type: "POST",
            url: "assets/contact.php",
            data: postdata,
            dataType: "json",
            success: function (json) {
                if (json.emailMessage != "") {
                    $(".section-6-form form .contact-email").addClass("input-error");
                }
                if (json.subjectMessage != "") {
                    $(".section-6-form form .contact-subject").addClass("input-error");
                }
                if (json.messageMessage != "") {
                    $(".section-6-form form textarea").addClass("input-error");
                }
                if (
                    json.emailMessage == "" &&
                    json.subjectMessage == "" &&
                    json.messageMessage == ""
                ) {
                    $(".section-6-form form").fadeOut("fast", function () {
                        $(".section-6-form").append(
                            "<p>Thanks for contacting us! We will get back to you very soon.</p>"
                        );
                        $(".section-6-container").backstretch("resize");
                    });
                }
            },
        });
    });
});

//Get the button:
mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
    scrollFunction();
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

//Image upload

function previewImage1() {
    var file = document.getElementById("file1").files;
    if (file.length > 0) {
        var fileReader = new FileReader();

        fileReader.onload = function (event) {
            document
                .getElementById("preview1")
                .setAttribute("src", event.target.result);
        };

        fileReader.readAsDataURL(file[0]);
    }
}

function previewImage2() {
    var file = document.getElementById("file2").files;
    if (file.length > 0) {
        var fileReader = new FileReader();

        fileReader.onload = function (event) {
            document
                .getElementById("preview2")
                .setAttribute("src", event.target.result);
        };

        fileReader.readAsDataURL(file[0]);
    }
}

function previewImage3() {
    var file = document.getElementById("file3").files;
    if (file.length > 0) {
        var fileReader = new FileReader();

        fileReader.onload = function (event) {
            document
                .getElementById("preview3")
                .setAttribute("src", event.target.result);
        };

        fileReader.readAsDataURL(file[0]);
    }
}

function disableSubmit() {
    document.getElementById("submit").disabled = true;
}

function activateButton(element) {

    if (element.checked) {
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("submit").disabled = true;
    }

}

$(document).ready(function () {
    var readURL = function (input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('.avatar').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }


    $(".file-upload").on('change', function () {
        readURL(this);
    });
});

function formatName(value) {
    let nameArray = value.split(" ");
    nameArray = nameArray.map(function (name) {
        return name.substring(0,1).toUpperCase() + name.substring(1,name.length).toLowerCase();
    });
    return nameArray.join(' ');
}

$("#fullname").blur(function (e) {
    let formatedName = formatName(e.target.value.trim());
    e.target.value = formatedName;
})
$("#birthdate").blur(function (e) {
    console.log(e.target.value);
})

function Validator(options) {
    var selectorRules = {};

    function removeErrMsg(inpElement) {
        inpElement.removeClass("input-error");
        inpElement.next().removeClass("input-error-message is-active");
        inpElement.next().text('');
    }

    function validate(inpElement, rule) {
        let errMsg;
        let rules = selectorRules[rule.selector];
        for (let i = 0; i < rules.length; ++i) {
            errMsg = rules[i](inpElement.val());
            if (errMsg) break;
        }
        if (errMsg) {
            inpElement.next().addClass("input-error-message is-active");
            inpElement.next().text(errMsg);
            inpElement.addClass("input-error");
        } else {
            removeErrMsg(inpElement);
        }
        return !errMsg;
    }

    let formEle = $(options.form);
    if (formEle) {
        formEle.submit(function (e){

            options.rules.forEach(function (rule) {
                let inpElement = formEle.find(rule.selector);
                let isValid = validate(inpElement, rule);
                if(!isValid){
                    e.preventDefault();
                }
            });
        });
        options.rules.forEach(function (rule) {
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }
            let inpElement = formEle.find(rule.selector);
            if (inpElement) {
                inpElement.blur(function () {
                    validate(inpElement, rule);
                })
                inpElement.on('input', function () {
                    removeErrMsg(inpElement);
                });
            }
        });
    }
}

Validator.isRequired = function (selector, msg) {
    return {
        selector: selector,
        test: function (value) {
            return value.trim() ? undefined : msg || 'Trường này không được để trống.'
        }
    }
}

Validator.isName = function (selector, msg) {
    return {
        selector: selector,
        test: function (value) {
            let regex = /^([a-vxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ]{2,})+\s+([a-vxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ\s]{2,})+$/i
            return regex.test(value) ? undefined : msg || 'Dữ liệu nhập vào không hợp lệ'
        }
    }
}

Validator.isEmail = function (selector, msg) {
    return {
        selector: selector,
        test: function (value) {
            let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
            return regex.test(value) ? undefined : msg || 'Email không đúng định dạng.'
        }
    }
}

Validator.isPhone = function (selector, msg) {
    return {
        selector: selector,
        test: function (value) {
            let regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
            return regex.test(value) ? undefined : msg || 'Số điện thoại không hợp lệ.'
        }
    }
}

Validator.isPassword = function (selector, msg) {
    return {
        selector: selector,
        test: function (value) {
            let regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
            return regex.test(value) ? undefined : msg || 'Mật khẩu không hợp lệ.'
        }
    }
}


Validator.minLength = function (selector, min, msg) {
    return {
        selector: selector,
        test: function (value) {
            return value.length >= min ? undefined : msg || `Độ dài tối thiểu ${min} kí tự.`;
        }
    }
}

Validator.maxLength = function (selector, max, msg) {
    return {
        selector: selector,
        test: function (value) {
            return value.length <= max ? undefined : msg || `Độ dài tối đa ${max} kí tự.`;
        }
    }
}

Validator.isMatch = function (selector, getValue, msg) {
    return {
        selector: selector,
        test: function (value) {
            return value === getValue() ? undefined : msg || `Giá trị nhập vào không khớp`;
        }
    }
}
