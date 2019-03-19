(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ConcurrentReviews', ConcurrentReviews);

    ConcurrentReviews.$inject = ['$resource', 'DateUtils'];

    function ConcurrentReviews ($resource, DateUtils) {
        var resourceUrl =  'api/concurrent-reviews/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.authorizationDate = DateUtils.convertDateTimeFromServer(data.authorizationDate);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                        data.lastCoverageDate = DateUtils.convertDateTimeFromServer(data.lastCoverageDate);
                        data.nextReviewDate = DateUtils.convertDateTimeFromServer(data.nextReviewDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
