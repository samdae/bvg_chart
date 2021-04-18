// SMS 발송
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});

// QR 재출력
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
// 회원정보 변경이력
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#change" ).on( "click", function() {
    $( ".open_change" ).dialog( "open" );
  });
});
// 교육비납입증명서
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#payment" ).on( "click", function() {
    $( ".open_payment" ).dialog( "open" );
  });
});
// 강좌등록이력
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#course" ).on( "click", function() {
    $( ".open_course" ).dialog( "open" );
  });
});
// 불량회원 등록
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#defect" ).on( "click", function() {
    $( ".open_sdefect" ).dialog( "open" );
  });
});
//회원 등록
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//회원 목록
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#memberRegiBtn" ).on( "click", function() {
	  $('#i_memberPicture').html('<img src=\"/images/noimage.png\" style=\"width:100%;\">');
    $( ".open_memberRegi" ).dialog( "open" );
  });
  $( "#memberModiMBtn" ).on( "click", function() {
	  $('#memberPicture').html('');
  });
});
//엑셀 다운로드
$( function() {
  $( "#excelDialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//사물함 등록
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//사물함 연장
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//사물함 이동
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//사물함 회수
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//수강연장
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//사물함 차액결제
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#lockerDifference" ).on( "click", function() {
    $( ".lockerDifference" ).dialog( "open" );
  });
});
//사진등록
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#memberPictureBtn" ).on( "click", function() {
    $( ".memberPictureArea" ).dialog( "open" );
  });
});
//사진수정
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#memberPictureModiBtn" ).on( "click", function() {
	  if($('#mem_no').val() != null && $('#mem_no').val() != '') {
		  $( ".memberPictureArea2" ).dialog( "open" );
	  } else {
		  alert('회원을 선택해 주세요.');
	  }
  });
});
//대기제
$( function() {
  $( "#wait_dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
//대기제 sms
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
});
// layout
$( function() {
  $( "#dialog" ).dialog({
    autoOpen: false,
    modal: true,
    width:'auto',
    show: {
      effect: "fade",
      duration: 500
    },
    hide: {
      effect: "fade",
      duration: 500
    }
  });
  $( "#opener" ).on( "click", function() {
    $( "#dialog" ).dialog( "open" );
  });
});

// datepicker
$( function() {
  $( "#datepicker" ).datepicker({
    changeMonth: true,
    changeYear: true
  });
});
// datepicker - from, to
$( function() {
  var dateFormat = "mm/dd/yy",
    from = $( "#from" )
      .datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1
      })
      .on( "change", function() {
        to.datepicker( "option", "minDate", getDate( this ) );
      }),
    to = $( "#to" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      changeYear: true,
      numberOfMonths: 1
    })
    .on( "change", function() {
      from.datepicker( "option", "maxDate", getDate( this ) );
    });

  function getDate( element ) {
    var date;
    try {
      date = $.datepicker.parseDate( dateFormat, element.value );
    } catch( error ) {
      date = null;
    }

    return date;
  }
} );

//tabs
$( function() {
  $( "#tabs" ).tabs();
});

//accordion
$( function() {
  $( "#accordion" ).accordion({
    heightStyle: "content"
  });
});

// data format
$( function() {
  $('#frmt').jstree({
      'core' : {
          'data' : [
              {
                  "text" : "Root node",
                  "state" : { "opened" : true },
                  "children" : [
                      {
                          "text" : "Child node 1",
                          "state" : { "selected" : true },
                          "icon" : "jstree-file"
                      },
                      { "text" : "Child node 2", "state" : { "disabled" : true } }
                  ]
              }
          ]
      }
    });
});
