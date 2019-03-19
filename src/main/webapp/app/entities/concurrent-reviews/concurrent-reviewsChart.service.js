(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ConcurrentReviewsChart', ConcurrentReviewsChart);

    ConcurrentReviewsChart.$inject = ['$resource', 'DateUtils'];

    function ConcurrentReviewsChart ($resource, DateUtils) {
        var resourceUrl =  'api/concurrent-reviews/chart/:id';

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
                        data.lastCoverageDate = DateUtils.convertDateTimeFromServer(data.last_coverageDate);
                        data.nextReviewDate = DateUtils.convertDateTimeFromServer(data.next_reviewDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
