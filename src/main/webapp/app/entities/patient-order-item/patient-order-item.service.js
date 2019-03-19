(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientOrderItem', PatientOrderItem);

    PatientOrderItem.$inject = ['$resource', 'DateUtils'];

    function PatientOrderItem ($resource, DateUtils) {
        var resourceUrl =  'api/patient-order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'all': { url: 'api/all-order-items/', method: 'GET', isArray: true},
            'getOrderItemsCollected': { url: 'api/order-items-collected/:id', method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.collectedDate = DateUtils.convertLocalDateFromServer(data.collectedDate);
                        data.scheduleDate = DateUtils.convertLocalDateFromServer(data.scheduleDate);
                        data.sentDate = DateUtils.convertLocalDateFromServer(data.sentDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.collectedDate = DateUtils.convertLocalDateToServer(copy.collectedDate);
                    copy.scheduleDate = DateUtils.convertLocalDateToServer(copy.scheduleDate);
                    copy.sentDate = DateUtils.convertLocalDateToServer(copy.sentDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.collectedDate = DateUtils.convertLocalDateToServer(copy.collectedDate);
                    copy.scheduleDate = DateUtils.convertLocalDateToServer(copy.scheduleDate);
                    copy.sentDate = DateUtils.convertLocalDateToServer(copy.sentDate);
                    return angular.toJson(copy);
                }
            },
            'cancel': { url: 'api/patient-order-items/cancel/:id', method: 'GET'}
        });
    }
})();
