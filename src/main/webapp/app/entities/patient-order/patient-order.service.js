(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientOrder', PatientOrder);

    PatientOrder.$inject = ['$resource', 'DateUtils'];

    function PatientOrder ($resource, DateUtils) {
        var resourceUrl =  'api/patient-orders/:id';

        return $resource(resourceUrl, {}, {
            // 'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.signatureDate = DateUtils.convertLocalDateFromServer(data.signatureDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);

                        for (var i = 0; i < data.patientOrderTests.length; i++) {
                            data.patientOrderTests[i].staringDate =  DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].staringDate);
                            if (data.patientOrderTests[i].endDate) {
                                data.patientOrderTests[i].endDate =  DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].endDate);
                            }

                            // for (var j = 0; j < data.patientOrderTests[i].patientOrderItems.length; j++) {
                            //     data.patientOrderTests[i].patientOrderItems[j].scheduleDate =
                            //         DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].patientOrderItems[j].scheduleDate);
                            // }
                        }
                    }

                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.signatureDate = DateUtils.convertLocalDateToServer(copy.signatureDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.signatureDate = DateUtils.convertLocalDateToServer(copy.signatureDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'orders': {
                url: 'api/orders-by-chart/:id', method: 'GET', isArray: true
            },
            'schedule': {
                url: 'api/patient-orders-schedule',  method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    if (copy.patientOrderTests !=null ) {
                        for (var i = 0; i < data.patientOrderTests.length; i++) {
                            copy.patientOrderTests[i].staringDate =  DateUtils.convertLocalDateToServer(copy.patientOrderTests[i].staringDate);
                            if (copy.patientOrderTests[i].endDate) {
                                copy.patientOrderTests[i].endDate =  DateUtils.convertLocalDateToServer(copy.patientOrderTests[i].endDate);
                            }
                        }
                    }

                    return angular.toJson(copy);
                },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.signatureDate = DateUtils.convertLocalDateFromServer(data.signatureDate);
                    data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);

                    for (var i = 0; i < data.patientOrderTests.length; i++) {
                        data.patientOrderTests[i].staringDate =  DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].staringDate);
                        if (data.patientOrderTests[i].endDate) {
                            data.patientOrderTests[i].endDate =  DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].endDate);
                        }

                        // for (var j = 0; j < data.patientOrderTests[i].patientOrderItems.length; j++) {
                        //     data.patientOrderTests[i].patientOrderItems[j].scheduleDate =
                        //         DateUtils.convertLocalDateFromServer(data.patientOrderTests[i].patientOrderItems[j].scheduleDate);
                        // }
                    }

                    return data;
                }
            },
            // 'hashstanding': {
            //     url: "api/patient-orders-hash-standing/:id", method: 'GET',
            //     transformResponse: function (data) {
            //         return data == "false" ? {value: false} : {value: true};
            //     }
            // },
            'cancel': {url: "api/cancel-patient-order/:id", method: 'GET'},
            'finish': {url: "api/finish-patient-order/:id", method: 'GET'},
            // 'ontime_old': {
            //     url: 'api/patient-orders-on-time-old/:id', method: 'GET', isArray: true
            // },
            // 'standing_old': {
            //     url: 'api/patient-orders-standing-old/:id', method: 'GET', isArray: true
            // },
            // 'recurring_old': {
            //     url: 'api/patient-orders-recurring-old/:id', method: 'GET', isArray: true
            // },
            'today_orders': {
                url: 'api/patient-orders/today/:id/:date', method: 'GET', isArray: true
            },
            'collect': {url: 'api/patient-order-collect', method: 'POST'},
            'not_collected' : {url : "api/count-orders-not-collected-by-date/:id/:date", method: 'GET',
                transformResponse: function (data) {
                    return {value: data};
                }},
            'unsigned' : {url : "api/patient-orders/unsigned/:id", method: 'GET', isArray: true},
            'all-unsigned' : {url : "api/patient-orders/unsigned/all/:id", method: 'GET', isArray: true},
            'signOrders': {url: 'api/patient-orders/sign-orders', method: 'POST'},
            'dashboardSchedule': { url: 'api/patient-orders/schedule/:id/:date', method: 'GET', isArray: true },
            'changeDrawDay': {url: 'api/patient-orders/change-draw-day', method: 'POST'},
            'getSignedBy': {url: 'api/patient-orders/get-signed-by/:id', method: 'GET'},
            'getPatientOrderTestItems': {url: 'api/patient-orders-test/get-patient-order-tests-items/:id', method: 'GET', isArray: true},
            'getBarcodes': {url: 'api/patient-orders/get-barcodes', method: 'POST', isArray: true},
            'getBarcode': {url: 'api/patient-orders/get-barcode/:id/:date', method: 'GET'}

        });
    }
})();
